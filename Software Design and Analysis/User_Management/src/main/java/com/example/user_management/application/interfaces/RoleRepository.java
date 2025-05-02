package com.example.user_management.application.interfaces;

import com.example.user_management.domain.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository {
    void save(Role role);
    Optional<Role> findById(UUID id);
}