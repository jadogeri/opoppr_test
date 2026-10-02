package com.opao.pp_api.repositories;

import com.opao.pp_api.repositories.entities.UserStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatusEntity, Integer> {

    // Note: Standard findAll() is already provided by JpaRepository

    // SELECT u FROM UserStatus u WHERE u.userStatusId = :userStatusId
    Optional<UserStatusEntity> findByUserStatusId(Integer userStatusId);

    // SELECT u FROM UserStatus u WHERE u.name = :name
    Optional<UserStatusEntity> findByName(String name);
}