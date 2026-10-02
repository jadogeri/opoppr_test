package com.opao.pp_api.repositories;

import com.opao.pp_api.repositories.entities.BusinessTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository 
public interface BusinessTypeRepository extends JpaRepository<BusinessTypeEntity, Integer> {
    Optional<BusinessTypeEntity> findByBusinessCode(Integer businessCode);
    Optional<BusinessTypeEntity> findByBusinessDescription(String businessDescription);
    Optional<BusinessTypeEntity> findByBusinessTypeId(Integer businessTypeId);
}
    