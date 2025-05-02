package com.example.user_management.infrastructure.persistence;

import com.example.user_management.application.interfaces.UserRepository;
import com.example.user_management.domain.Role;
import com.example.user_management.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserJpaRepository implements UserRepository {
    private final UserJpaRepositoryInterface jpaRepository;
    private final RoleJpaRepository roleJpaRepository;

    public UserJpaRepository(UserJpaRepositoryInterface jpaRepository, RoleJpaRepository roleJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public void save(User user) {
        UserJpaEntity entity = toJpaEntity(user);
        jpaRepository.save(entity);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(this::toDomainEntity);
    }

    private UserJpaEntity toJpaEntity(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());

        List<Role> roles = user.getRoles();
        List<RoleJpaEntity> roleJpaEntities = new ArrayList<>();

        for(Role role: roles){
            Optional<Role> role1 = roleJpaRepository.findById(role.getId());
            if(role1.isPresent()){
                RoleJpaEntity newRoleEntity = new RoleJpaEntity();
                newRoleEntity.setId(role.getId());
                newRoleEntity.setRoleName(role.getRoleName());
                roleJpaEntities.add(newRoleEntity);
            }
        }
        entity.setRoles(roleJpaEntities);

//        entity.setRoles(user.getRoles()
//                .stream().map(role -> roleJpaRepository.findById(role.getId())
//                        .orElseGet(() -> {
//                            RoleJpaEntity newRoleEntity = new RoleJpaEntity();
//                            newRoleEntity.setId(role.getId());
//                            newRoleEntity.setRoleName(role.getRoleName());
//                            return newRoleEntity;
//                        }))
//                .collect(Collectors.toList()));

        return entity;
    }


    private User toDomainEntity(UserJpaEntity entity) {
        User user = new User(entity.getId(), entity.getName(), entity.getEmail());
        entity.getRoles().forEach(roleEntity ->
                user.addRole(new Role(roleEntity.getId(), roleEntity.getRoleName())));
        return user;
    }

}

interface UserJpaRepositoryInterface extends org.springframework.data.jpa.repository.JpaRepository<UserJpaEntity, UUID> {
}