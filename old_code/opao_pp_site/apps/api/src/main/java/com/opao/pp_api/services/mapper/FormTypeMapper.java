package com.opao.pp_api.services.mapper;

import com.opao.pp_api.repositories.entities.FormTypeEntity;
import com.opao.pp_api.services.domains.FormType;
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
public interface FormTypeMapper {

    /**
     * READ: Converts a database FormTypeEntity into a clean business FormType domain model.
     */
    @Mapping(source = "formTypeId", target = "id")
    @Mapping(source = "formName", target = "name")
    FormType toDomain(FormTypeEntity entity);

    /**
     * CREATE: Converts a business FormType model into a brand-new database FormTypeEntity.
     */
    @Mapping(source = "id", target = "formTypeId")
    @Mapping(source = "name", target = "formName")
    FormTypeEntity toEntity(FormType domain);

    /**
     * UPDATE: Merges incremental updates from your business domain into an EXISTING database entity safely.
     */
    @Mapping(target = "formTypeId", ignore = true) // Protect the Primary Key from accidental mutation
    @Mapping(source = "name", target = "formName")
    FormTypeEntity updateEntityFromDomain(FormType domain, @MappingTarget FormTypeEntity existingEntity);
}
