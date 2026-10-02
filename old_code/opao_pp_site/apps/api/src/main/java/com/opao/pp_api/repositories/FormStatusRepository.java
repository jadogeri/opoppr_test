package com.opao.pp_api.repositories;

import com.opao.pp_api.repositories.entities.FormStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormStatusRepository extends JpaRepository<FormStatusEntity, Integer> {

    // Note: Standard findAll() is already provided by JpaRepository

    // SELECT s FROM Status s WHERE s.statusId = :statusId
    Optional<FormStatusEntity> findByStatusId(Integer statusId);

    // SELECT s FROM Status s WHERE s.name = :name
    Optional<FormStatusEntity> findByName(String name);
}
