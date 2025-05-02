package com.example.user_management.infrastructure.controller;

import com.example.user_management.application.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Map<String, UUID>> createUser(@RequestBody CreateUserRequest request) {
        UUID userId = userService.createUser(request.name, request.email);
        Map<String, UUID> response = new HashMap<>();
        response.put("userId", userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{userId}/assign-role/{roleId}")
    public ResponseEntity<Map<String, String>> assignRole(
            @PathVariable UUID userId,
            @PathVariable UUID roleId) {
        userService.assignRoleToUser(userId, roleId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Role assigned successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserDetails(@PathVariable UUID id) {
        return userService.getUserDetails(id)
                .map(user -> ResponseEntity.ok(new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRoles().stream()
                                .map(role -> new RoleResponse(role.getId(), role.getRoleName()))
                                .toList())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    static class CreateUserRequest {
        public String name;
        public String email;
    }

    static class UserResponse {
        public UUID id;
        public String name;
        public String email;
        public List<RoleResponse> roles;

        public UserResponse(UUID id, String name, String email, List<RoleResponse> roles) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.roles = roles;
        }
    }

    static class RoleResponse {
        public UUID id;
        public String roleName;

        public RoleResponse(UUID id, String roleName) {
            this.id = id;
            this.roleName = roleName;
        }
    }
}