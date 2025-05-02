package com.example.user_management.infrastructure.persistence;

import com.example.user_management.application.interfaces.RoleRepository;
import com.example.user_management.domain.Role;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RoleJpaRepository implements RoleRepository {
    private final RoleJpaRepositoryInterface jpaRepository;

    public RoleJpaRepository(RoleJpaRepositoryInterface jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Role role) {
        RoleJpaEntity entity = toJpaEntity(role);
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Role> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(this::toDomainEntity);
    }

    private RoleJpaEntity toJpaEntity(Role role) {
        RoleJpaEntity entity = new RoleJpaEntity();
        entity.setId(role.getId());
        entity.setRoleName(role.getRoleName());
        return entity;
    }

    private Role toDomainEntity(RoleJpaEntity entity) {
        return new Role(entity.getId(), entity.getRoleName());
    }
}

interface RoleJpaRepositoryInterface extends org.springframework.data.jpa.repository.JpaRepository<RoleJpaEntity, UUID> {
}