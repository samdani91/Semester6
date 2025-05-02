package com.example.user_management.application;

import com.example.user_management.application.interfaces.UserRepository;
import com.example.user_management.domain.Role;
import com.example.user_management.domain.User;

import java.util.Optional;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public UUID createUser(String name, String email) {
        validateName(name);
        validateEmail(email);

        UUID id = UUID.randomUUID();
        User user = new User(id, name, email);
        userRepository.save(user);
        return id;
    }

    public void assignRoleToUser(UUID userId, UUID roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Role role = roleService.findRoleById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        user.addRole(role);
        userRepository.save(user);
    }

    public Optional<User> getUserDetails(UUID id) {
        return userRepository.findById(id);
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
    }

    private void validateEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}