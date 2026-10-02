package com.opao.pp_api.services;

import com.opao.pp_api.repositories.BusinessTypeRepository;
import com.opao.pp_api.repositories.entities.BusinessTypeEntity;
import com.opao.pp_api.services.domains.BusinessType;
import com.opao.pp_api.services.mapper.BusinessTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusinessTypeService {

    private final BusinessTypeRepository businessTypeRepository;
    private final BusinessTypeMapper businessTypeMapper;

    public BusinessTypeService(BusinessTypeRepository businessTypeRepository, BusinessTypeMapper businessTypeMapper) {
        this.businessTypeRepository = businessTypeRepository;
        this.businessTypeMapper = businessTypeMapper;
    }

    @Transactional(readOnly = true)
    public List<BusinessType> getAllBusinessTypes() {
        return businessTypeRepository.findAll().stream()
                .map(businessTypeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<BusinessType> getBusinessTypeById(Integer id) {
        return businessTypeRepository.findById(id)
                .map(businessTypeMapper::toDomain);
    }

    @Transactional
    public BusinessType createBusinessType(BusinessType domainModel) {
        // Enforce uniqueness check similar to the username validation in UserService
        if (businessTypeRepository.findByBusinessCode(domainModel.getCode()).isPresent()) {
            throw new IllegalArgumentException("Business code is already registered");
        }

        BusinessTypeEntity entity = businessTypeMapper.toEntity(domainModel);
        BusinessTypeEntity savedEntity = businessTypeRepository.save(entity);
        return businessTypeMapper.toDomain(savedEntity);
    }

    @Transactional
    public Optional<BusinessType> updateBusinessType(Integer id, BusinessType updatedBusinessType) {
        return businessTypeRepository.findById(id).map(existingEntity -> {
            // Safely merge incremental domain updates into the tracked database entity
            businessTypeMapper.updateEntityFromDomain(updatedBusinessType, existingEntity);
            BusinessTypeEntity savedEntity = businessTypeRepository.save(existingEntity);
            return businessTypeMapper.toDomain(savedEntity);
        });
    }

    @Transactional
    public boolean deleteBusinessType(Integer id) {
        if (businessTypeRepository.existsById(id)) {
            businessTypeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
