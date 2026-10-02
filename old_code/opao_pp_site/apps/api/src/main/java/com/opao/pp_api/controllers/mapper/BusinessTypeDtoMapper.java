package com.opao.pp_api.controllers.mapper;

import com.opao.pp_api.controllers.dto.request.BusinessTypeRequest;
import com.opao.pp_api.controllers.dto.response.BusinessTypeResponse;
import com.opao.pp_api.repositories.entities.BusinessTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class BusinessTypeDtoMapper {

    public BusinessTypeEntity toEntity(BusinessTypeRequest request) {
        if (request == null) return null;
        
        BusinessTypeEntity entity = new BusinessTypeEntity();
        entity.setBusinessCode(request.code());
        entity.setBusinessDescription(request.description());
        return entity;
    }

    public void updateEntityFromRequest(BusinessTypeRequest request, BusinessTypeEntity entity) {
        if (request == null || entity == null) return;
        
        entity.setBusinessCode(request.code());
        entity.setBusinessDescription(request.description());
    }

    public BusinessTypeResponse toResponse(BusinessTypeEntity entity) {
        if (entity == null) return null;
        
        return new BusinessTypeResponse(
            entity.getBusinessTypeId(),
            entity.getBusinessCode(),
            entity.getBusinessDescription()
        );
    }
}
