package com.opao.pp_api.controllers;

import com.opao.pp_api.services.BusinessTypeService;
import com.opao.pp_api.services.domains.BusinessType;
import com.opao.pp_api.controllers.mapper.BusinessTypeDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business-types")
@Tag(name = "Business Types", description = "CRUD endpoints for business types")
public class BusinessTypeController {

    private final BusinessTypeService businessTypeService;
    private final BusinessTypeDtoMapper businessTypeDtoMapper;

    // Injects the refactored service layer
    public BusinessTypeController(BusinessTypeService businessTypeService, BusinessTypeDtoMapper businessTypeDtoMapper) {
        this.businessTypeService = businessTypeService;
        this.businessTypeDtoMapper = businessTypeDtoMapper;
    }

    @GetMapping
    @Operation(summary = "Get all business types", description = "Fetches a complete array of all registered business types")
    public ResponseEntity<List<BusinessType>> getAll() { 
        return ResponseEntity.ok(businessTypeService.getAllBusinessTypes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find business type by ID")
    public ResponseEntity<BusinessType> getById(@PathVariable Integer id) { 
        return businessTypeService.getBusinessTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/count")
    @Operation(summary = "Get the total number of business types")
    public ResponseEntity<Long> getBusinessTypeCount() {
        // Obtains the global record count directly from the underlying database context
        long count = businessTypeService.getAllBusinessTypes().size(); 
        return ResponseEntity.ok(count);
    }
}
