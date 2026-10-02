package com.opao.pp_api.services.mapper;

import com.opao.pp_api.repositories.entities.NoaPpLat5FilingEntity;
import com.opao.pp_api.services.domains.NoaPpLat5Filing;

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
    uses = { PropertyAssetMapper.class } // Handles PropertyAssetEntity <-> PropertyAsset
)
public interface NoaPpLat5FilingMapper {

    /**
     * READ: Converts database entity graphs down into your clean business domain model.
     */
    @Mapping(source = "noaPpLat5FilingId", target = "id")
    @Mapping(source = "jur", target = "jurisdiction")
    @Mapping(source = "parid", target = "parcelId")
    @Mapping(source = "taxyr", target = "taxYear")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "pptype", target = "propertyType")
    @Mapping(source = "fileyr", target = "filingYear")
    @Mapping(source = "yracqd", target = "yearAcquired")
    @Mapping(source = "nounits", target = "noOfUnits")
    @Mapping(source = "acquisitionCost", target = "acquisitionCost")
    @Mapping(source = "effectiveLife", target = "effectiveLife")
    @Mapping(source = "consignerOwnerName", target = "consignerOwnerName")
    @Mapping(source = "consignerMailingAddr", target = "consignerMailingAddr")
    @Mapping(source = "consignerRentalAmt", target = "consignerRentalAmt")
    @Mapping(source = "itemDescription", target = "itemDescription")
    @Mapping(source = "consignerTelNo", target = "consignerTelNo")
    @Mapping(source = "noaPpLat5.noaPpLat5Id", target = "noaPpLat5Id")
    @Mapping(source = "propertyAsset", target = "propertyAsset")
    NoaPpLat5Filing toDomain(NoaPpLat5FilingEntity entity);

    /**
     * CREATE: Converts your business domain model back down into a pristine database row.
     */
    @Mapping(source = "id", target = "noaPpLat5FilingId")
    @Mapping(source = "jurisdiction", target = "jur")
    @Mapping(source = "parcelId", target = "parid")
    @Mapping(source = "taxYear", target = "taxyr")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "propertyType", target = "pptype")
    @Mapping(source = "filingYear", target = "fileyr")
    @Mapping(source = "yearAcquired", target = "yracqd")
    @Mapping(source = "noOfUnits", target = "nounits")
    @Mapping(source = "acquisitionCost", target = "acquisitionCost")
    @Mapping(source = "effectiveLife", target = "effectiveLife")
    @Mapping(source = "consignerOwnerName", target = "consignerOwnerName")
    @Mapping(source = "consignerMailingAddr", target = "consignerMailingAddr")
    @Mapping(source = "consignerRentalAmt", target = "consignerRentalAmt")
    @Mapping(source = "itemDescription", target = "itemDescription")
    @Mapping(source = "consignerTelNo", target = "consignerTelNo")
    @Mapping(target = "noaPpLat5", ignore = true) // Relational parent references linked manually in Service Tier
    @Mapping(source = "propertyAsset", target = "propertyAsset")
    NoaPpLat5FilingEntity toEntity(NoaPpLat5Filing domain);

    /**
     * UPDATE: Modifies an existing database entity smoothly using partial domain inputs.
     */
    @Mapping(target = "noaPpLat5FilingId", ignore = true) // Protect the Database Primary Key from mutation
    @Mapping(source = "jurisdiction", target = "jur")
    @Mapping(source = "parcelId", target = "parid")
    @Mapping(source = "taxYear", target = "taxyr")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "propertyType", target = "pptype")
    @Mapping(source = "filingYear", target = "fileyr")
    @Mapping(source = "yearAcquired", target = "yracqd")
    @Mapping(source = "noOfUnits", target = "nounits")
    @Mapping(source = "acquisitionCost", target = "acquisitionCost")
    @Mapping(source = "effectiveLife", target = "effectiveLife")
    @Mapping(source = "consignerOwnerName", target = "consignerOwnerName")
    @Mapping(source = "consignerMailingAddr", target = "consignerMailingAddr")
    @Mapping(source = "consignerRentalAmt", target = "consignerRentalAmt")
    @Mapping(source = "itemDescription", target = "itemDescription")
    @Mapping(source = "consignerTelNo", target = "consignerTelNo")
    @Mapping(target = "noaPpLat5", ignore = true)
    @Mapping(target = "propertyAsset", ignore = true) // Protect structural reference fields from change overrides
    NoaPpLat5FilingEntity updateEntityFromDomain(NoaPpLat5Filing domain, @MappingTarget NoaPpLat5FilingEntity existingEntity);
}
