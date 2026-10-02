package com.opao.pp_api.repositories;

import com.opao.pp_api.repositories.entities.PropertyAssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyAssetRepository extends JpaRepository<PropertyAssetEntity, Integer> {

    // Note: Standard findAll() is already provided by JpaRepository

    // SELECT p FROM PropertyAsset p WHERE p.propertyAssetId = :propertyAssetId
    Optional<PropertyAssetEntity> findByPropertyAssetId(Integer propertyAssetId);

    // SELECT p FROM PropertyAsset p WHERE p.sectionNumber = :sectionNumber
    List<PropertyAssetEntity> findBySectionNumber(String sectionNumber);

    // FIXED: Changed OrderByIdAsc to OrderByPropertyAssetIdAsc to match the Entity primary key property
    List<PropertyAssetEntity> findBySectionNumberOrderByPropertyAssetIdAsc(String sectionNumber);

    // SELECT p FROM PropertyAsset p WHERE p.category = :category
    List<PropertyAssetEntity> findByCategory(String category);

    // SELECT p FROM PropertyAsset p WHERE p.pptype = :pptype
    List<PropertyAssetEntity> findByPptype(String pptype);

    // SELECT p FROM PropertyAsset p WHERE p.assetDescription = :assetDescription
    List<PropertyAssetEntity> findByAssetDescription(String assetDescription);

    // SELECT p FROM PropertyAsset p WHERE p.effectiveLife = :effectiveLife
    List<PropertyAssetEntity> findByEffectiveLife(Integer effectiveLife);
}
