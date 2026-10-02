package com.opao.pp_api.services.mapper;

import com.opao.pp_api.repositories.entities.NoaPpLat5Entity;
import com.opao.pp_api.services.domains.NoaPpLat5;

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
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = { 
        BusinessTypeMapper.class, 
        NoaPpLat5FilingMapper.class, 
        NoaPpLat5InventoriesMapper.class 
    }
)
public interface NoaPpLat5Mapper {

    /**
     * READ: Converts database entity graphs down into your clean business domain model.
     */
    @Mapping(source = "noaPpLat5Id", target = "id")
    @Mapping(source = "jur", target = "jurisdiction")
    @Mapping(source = "parid", target = "parcelId")
    @Mapping(source = "altid", target = "alternateId")
    @Mapping(source = "taxyr", target = "taxYear")
    @Mapping(source = "ownername", target = "ownerName")
    @Mapping(source = "addr1", target = "address1")
    @Mapping(source = "addr2", target = "address2")
    @Mapping(source = "cityname", target = "cityName")
    @Mapping(source = "statecode", target = "stateCode")
    @Mapping(source = "zip1", target = "zipCode")
    @Mapping(source = "form.formId", target = "formId")
    @Mapping(source = "businessType", target = "businessType") 
    @Mapping(source = "noaPpLat5FilingCollection", target = "filings") 
    @Mapping(source = "noaPpLat5InventoriesCollection", target = "inventories") 
    NoaPpLat5 toDomain(NoaPpLat5Entity entity);

    /**
     * CREATE: Converts business domain model back down into a pristine database row.
     */
    @Mapping(source = "id", target = "noaPpLat5Id")
    @Mapping(source = "jurisdiction", target = "jur")
    @Mapping(source = "parcelId", target = "parid")
    @Mapping(source = "alternateId", target = "altid")
    @Mapping(source = "taxYear", target = "taxyr")
    @Mapping(source = "ownerName", target = "ownername")
    @Mapping(source = "address1", target = "addr1")
    @Mapping(source = "address2", target = "addr2")
    @Mapping(source = "cityName", target = "cityname")
    @Mapping(source = "stateCode", target = "statecode")
    @Mapping(source = "zipCode", target = "zip1")
    @Mapping(target = "form", ignore = true) // Handled safely via Service tier proxy links
    @Mapping(target = "businessType", ignore = true) // Handled safely via Service tier proxy links
    @Mapping(source = "filings", target = "noaPpLat5FilingCollection")
    @Mapping(source = "inventories", target = "noaPpLat5InventoriesCollection")
    NoaPpLat5Entity toEntity(NoaPpLat5 domain);

    /**
     * UPDATE: Modifies an existing database entity smoothly using partial domain inputs.
     */
    @Mapping(target = "noaPpLat5Id", ignore = true) // Safety protection: Protect primary key from updates
    @Mapping(source = "jurisdiction", target = "jur")
    @Mapping(source = "parcelId", target = "parid")
    @Mapping(source = "alternateId", target = "altid")
    @Mapping(source = "taxYear", target = "taxyr")
    @Mapping(source = "ownerName", target = "ownername")
    @Mapping(source = "address1", target = "addr1")
    @Mapping(source = "address2", target = "addr2")
    @Mapping(source = "cityName", target = "cityname")
    @Mapping(source = "stateCode", target = "statecode")
    @Mapping(source = "zipCode", target = "zip1")
    @Mapping(target = "form", ignore = true)
    @Mapping(target = "businessType", ignore = true)
    @Mapping(source = "filings", target = "noaPpLat5FilingCollection")
    @Mapping(source = "inventories", target = "noaPpLat5InventoriesCollection")
    NoaPpLat5Entity updateEntityFromDomain(NoaPpLat5 domain, @MappingTarget NoaPpLat5Entity existingEntity);
}
