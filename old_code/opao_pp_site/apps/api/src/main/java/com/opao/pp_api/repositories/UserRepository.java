package com.opao.pp_api.repositories;

import com.opao.pp_api.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Note: Standard findAll() is already provided by JpaRepository

    // SELECT u FROM User u WHERE u.userId = :userId
    Optional<UserEntity> findByUserId(Long userId);

    // SELECT u FROM User u WHERE u.username = :username
    Optional<UserEntity> findByUsername(String username);

    // SELECT u FROM User u WHERE u.username = :username AND u.userStatus.name = 'Enabled'
    @Query("SELECT u FROM UserEntity u WHERE u.username = :username AND u.userStatus.name = 'Enabled'")
    Optional<UserEntity> findByUsernameAndEnabled(@Param("username") String username);
    
    // Alternative Spring Data derived method (requires passing "Enabled" as statusName parameter):
    // Optional<UserEntity> findByUsernameAndUserStatusName(String username, String statusName);

    // SELECT u FROM User u WHERE u.password = :password
    List<UserEntity> findByPassword(String password);

    // SELECT u from User u WHERE u.username = :username AND u.password = :password
    Optional<UserEntity> findByUsernameAndPassword(String username, String password);

    // SELECT u from User u WHERE u.username = :username AND u.emailAddress = :emailAddress
    Optional<UserEntity> findByUsernameAndEmailAddress(String username, String emailAddress);

    // SELECT u FROM User u WHERE u.fullName = :fullName
    List<UserEntity> findByFullName(String fullName);

    // SELECT u FROM User u WHERE u.emailAddress = :emailAddress
    Optional<UserEntity> findByEmailAddress(String emailAddress);

    // SELECT u FROM User u WHERE u.creationTime = :creationTime
    List<UserEntity> findByCreationTime(Date creationTime);
}
