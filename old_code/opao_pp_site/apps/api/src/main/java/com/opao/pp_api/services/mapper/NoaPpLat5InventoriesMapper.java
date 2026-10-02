package com.opao.pp_api.services.mapper;

import com.opao.pp_api.repositories.entities.NoaPpLat5InventoriesEntity;
import com.opao.pp_api.services.domains.NoaPpLat5Inventories;

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
public interface NoaPpLat5InventoriesMapper {

    /**
     * READ: Converts database entity graphs down into your clean business domain model.
     */
    @Mapping(source = "noaPpLat5InventoriesId", target = "id")
    @Mapping(source = "jur", target = "jurisdiction")
    @Mapping(source = "parid", target = "parcelId")
    @Mapping(source = "taxyr", target = "taxYear")
    @Mapping(source = "fileyr", target = "filingYear")
    @Mapping(source = "inventoryType", target = "inventoryType")
    @Mapping(source = "inventoryMonth", target = "inventoryMonth")
    @Mapping(source = "inventoryAmt", target = "inventoryAmount")
    @Mapping(source = "noaPpLat5.noaPpLat5Id", target = "noaPpLat5Id")
    NoaPpLat5Inventories toDomain(NoaPpLat5InventoriesEntity entity);

    /**
     * CREATE: Converts business domain model back down into a pristine database row.
     */
    @Mapping(source = "id", target = "noaPpLat5InventoriesId")
    @Mapping(source = "jurisdiction", target = "jur")
    @Mapping(source = "parcelId", target = "parid")
    @Mapping(source = "taxYear", target = "taxyr")
    @Mapping(source = "filingYear", target = "fileyr")
    @Mapping(source = "inventoryType", target = "inventoryType")
    @Mapping(source = "inventoryMonth", target = "inventoryMonth")
    @Mapping(source = "inventoryAmount", target = "inventoryAmt")
    @Mapping(target = "noaPpLat5", ignore = true) // Parental references linked safely via Service Tier proxy logic
    NoaPpLat5InventoriesEntity toEntity(NoaPpLat5Inventories domain);

    /**
     * UPDATE: Modifies an existing database entity smoothly using partial domain inputs.
     */
    @Mapping(target = "noaPpLat5InventoriesId", ignore = true) // Protect the Database Primary Key from mutation
    @Mapping(source = "jurisdiction", target = "jur")
    @Mapping(source = "parcelId", target = "parid")
    @Mapping(source = "taxYear", target = "taxyr")
    @Mapping(source = "filingYear", target = "fileyr")
    @Mapping(source = "inventoryType", target = "inventoryType")
    @Mapping(source = "inventoryMonth", target = "inventoryMonth")
    @Mapping(source = "inventoryAmount", target = "inventoryAmt")
    @Mapping(target = "noaPpLat5", ignore = true)
    NoaPpLat5InventoriesEntity updateEntityFromDomain(NoaPpLat5Inventories domain, @MappingTarget NoaPpLat5InventoriesEntity existingEntity);
}
