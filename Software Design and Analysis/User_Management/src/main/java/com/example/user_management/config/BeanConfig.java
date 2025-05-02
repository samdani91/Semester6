package com.example.user_management.config;

import com.example.user_management.application.RoleService;
import com.example.user_management.application.UserService;
import com.example.user_management.application.interfaces.RoleRepository;
import com.example.user_management.application.interfaces.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public UserService userService(UserRepository userRepository, RoleService roleService) {
        return new UserService(userRepository, roleService);
    }

    @Bean
    public RoleService roleService(RoleRepository roleRepository) {
        return new RoleService(roleRepository);
    }
}
