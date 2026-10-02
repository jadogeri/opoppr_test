package com.opao.pp_api.services.mapper;

import com.opao.pp_api.repositories.entities.UserRoleEntity;
import com.opao.pp_api.services.domains.UserRole;
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
public interface UserRoleMapper {

    /**
     * READ: Converts a database UserRoleEntity into a clean business UserRole domain model.
     */
    @Mapping(source = "userRoleId", target = "id")
    UserRole toDomain(UserRoleEntity entity);

    /**
     * CREATE: Converts a business UserRole model into a brand-new database UserRoleEntity.
     */
    @Mapping(source = "id", target = "userRoleId")
    UserRoleEntity toEntity(UserRole domain);

    /**
     * UPDATE: Merges incremental updates from your business domain into an EXISTING database entity safely.
     */
    @Mapping(target = "userRoleId", ignore = true) // Protect the Primary Key / Constants from accidental mutation
    UserRoleEntity updateEntityFromDomain(UserRole domain, @MappingTarget UserRoleEntity existingEntity);
}
