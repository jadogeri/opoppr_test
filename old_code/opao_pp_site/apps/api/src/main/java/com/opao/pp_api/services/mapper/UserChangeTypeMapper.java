package com.opao.pp_api.services.mapper;

import com.opao.pp_api.repositories.entities.UserChangeTypeEntity; // Assuming standard entity location
import com.opao.pp_api.services.domains.UserChangeType;
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
public interface UserChangeTypeMapper {

    /**
     * READ: Converts a database UserChangeTypeEntity into a clean business UserChangeType domain model.
     */
    @Mapping(source = "userChangeTypeId", target = "id")
    UserChangeType toDomain(UserChangeTypeEntity entity);

    /**
     * CREATE: Converts a business UserChangeType model into a brand-new database UserChangeTypeEntity.
     */
    @Mapping(source = "id", target = "userChangeTypeId")
    UserChangeTypeEntity toEntity(UserChangeType domain);

    /**
     * UPDATE: Merges incremental updates from your business domain into an EXISTING database entity safely.
     */
    @Mapping(target = "userChangeTypeId", ignore = true) // Protect the Primary Key from accidental mutation
    UserChangeTypeEntity updateEntityFromDomain(UserChangeType domain, @MappingTarget UserChangeTypeEntity existingEntity);
}
