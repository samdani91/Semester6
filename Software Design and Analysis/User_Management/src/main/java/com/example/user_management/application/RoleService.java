package com.example.user_management.application;

import com.example.user_management.application.interfaces.RoleRepository;
import com.example.user_management.domain.Role;

import java.util.Optional;
import java.util.UUID;

public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UUID createRole(String roleName) {
        validateRoleName(roleName);

        UUID id = UUID.randomUUID();
        Role role = new Role(id, roleName);
        roleRepository.save(role);
        return id;
    }

    public Optional<Role> findRoleById(UUID id) {
        return roleRepository.findById(id);
    }

    private void validateRoleName(String roleName) {
        if (roleName == null || roleName.trim().isEmpty()) {
            throw new IllegalArgumentException("Role name cannot be blank");
        }
    }
}