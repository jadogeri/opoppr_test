package com.opao.pp_api.repositories;

import com.opao.pp_api.repositories.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Integer> {

    // Note: Standard findAll() is already provided by JpaRepository

    // SELECT u FROM UserRole u WHERE u.userRoleId = :userRoleId
    Optional<UserRoleEntity> findByUserRoleId(Integer userRoleId);

    // SELECT u FROM UserRole u WHERE u.name = :name
    Optional<UserRoleEntity> findByName(String name);

    // SELECT u FROM UserRole u WHERE u.description = :description
    List<UserRoleEntity> findByDescription(String description);
}