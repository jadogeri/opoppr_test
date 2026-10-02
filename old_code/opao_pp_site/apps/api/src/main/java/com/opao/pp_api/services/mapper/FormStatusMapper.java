package com.opao.pp_api.services.mapper;

import com.opao.pp_api.repositories.entities.FormStatusEntity;
import com.opao.pp_api.services.domains.FormStatus;
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
public interface FormStatusMapper {

    /**
     * READ: Converts a database FormStatusEntity into a clean business FormStatus domain model.
     */
    @Mapping(source = "statusId", target = "id")
    FormStatus toDomain(FormStatusEntity entity);

    /**
     * CREATE: Converts a business FormStatus model into a brand-new database FormStatusEntity.
     */
    @Mapping(source = "id", target = "statusId")
    FormStatusEntity toEntity(FormStatus domain);

    /**
     * UPDATE: Merges incremental updates from your business domain into an EXISTING database entity safely.
     */
    @Mapping(target = "statusId", ignore = true) // Protect the Primary Key from accidental mutation
    FormStatusEntity updateEntityFromDomain(FormStatus domain, @MappingTarget FormStatusEntity existingEntity);
}
