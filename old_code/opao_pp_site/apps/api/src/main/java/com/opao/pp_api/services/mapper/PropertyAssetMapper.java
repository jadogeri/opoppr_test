package com.opao.pp_api.services.mapper;

import com.opao.pp_api.repositories.entities.PropertyAssetEntity;
import com.opao.pp_api.services.domains.PropertyAsset;
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
public interface PropertyAssetMapper {

    /**
     * READ: Converts a database PropertyAssetEntity into a clean business PropertyAsset domain model.
     */
    @Mapping(source = "propertyAssetId", target = "id")
    @Mapping(source = "pptype", target = "propertyType")
    PropertyAsset toDomain(PropertyAssetEntity entity);

    /**
     * CREATE: Converts a business PropertyAsset model into a brand-new database PropertyAssetEntity.
     */
    @Mapping(source = "id", target = "propertyAssetId")
    @Mapping(source = "propertyType", target = "pptype")
    PropertyAssetEntity toEntity(PropertyAsset domain);

    /**
     * UPDATE: Merges incremental updates from your business domain into an EXISTING database entity safely.
     */
    @Mapping(target = "propertyAssetId", ignore = true) // Protect the Primary Key from accidental mutation
    @Mapping(source = "propertyType", target = "pptype")
    PropertyAssetEntity updateEntityFromDomain(PropertyAsset domain, @MappingTarget PropertyAssetEntity existingEntity);
}
