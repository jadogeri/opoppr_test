package com.opao.pp_api.repositories;

import com.opao.pp_api.repositories.entities.UserChangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserChangeRepository extends JpaRepository<UserChangeEntity, Long> {

    // Note: Standard findAll() is already provided by JpaRepository

    // SELECT u FROM UserChange u WHERE u.userChangeId = :userChangeId
    Optional<UserChangeEntity> findByUserChangeId(Long userChangeId);

    // SELECT u FROM UserChange u WHERE u.verificationCode = :verificationCode
    Optional<UserChangeEntity> findByVerificationCode(String verificationCode);

    // SELECT u FROM UserChange u WHERE u.initiatedTime = :initiatedTime
    List<UserChangeEntity> findByInitiatedTime(Date initiatedTime);
}
