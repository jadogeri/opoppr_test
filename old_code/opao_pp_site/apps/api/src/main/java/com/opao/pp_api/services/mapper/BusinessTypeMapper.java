package com.opao.pp_api.services.mapper;

import com.opao.pp_api.repositories.entities.BusinessTypeEntity;
import com.opao.pp_api.services.domains.BusinessType;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BusinessTypeMapper {

    /**
     * READ: Converts a database BusinessTypeEntity into a clean business BusinessType model.
     */
    @Mapping(source = "businessTypeId", target = "id")
    @Mapping(source = "businessCode", target = "code")
    @Mapping(source = "businessDescription", target = "description")
    BusinessType toDomain(BusinessTypeEntity entity);

    /**
     * CREATE: Converts a business BusinessType model into a brand-new database BusinessTypeEntity.
     */
    @Mapping(source = "id", target = "businessTypeId")
    @Mapping(source = "code", target = "businessCode")
    @Mapping(source = "description", target = "businessDescription")
    BusinessTypeEntity toEntity(BusinessType domain);

    /**
     * UPDATE: Merges incremental updates from your business domain into an EXISTING database entity safely.
     */
    @Mapping(target = "businessTypeId", ignore = true) // Protect the Primary Key from accidental mutation
    @Mapping(source = "code", target = "businessCode")
    @Mapping(source = "description", target = "businessDescription")
    BusinessTypeEntity updateEntityFromDomain(BusinessType domain, @MappingTarget BusinessTypeEntity existingEntity);
}
