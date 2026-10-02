package com.opao.pp_api.services.mapper;

import com.opao.pp_api.repositories.entities.UserStatusEntity;
import com.opao.pp_api.services.domains.UserStatus;
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
public interface UserStatusMapper {

    /**
     * READ: Converts a database UserStatusEntity into a clean business UserStatus domain model.
     */
    @Mapping(source = "userStatusId", target = "id")
    UserStatus toDomain(UserStatusEntity entity);

    /**
     * CREATE: Converts a business UserStatus model into a brand-new database UserStatusEntity.
     */
    @Mapping(source = "id", target = "userStatusId")
    UserStatusEntity toEntity(UserStatus domain);

    /**
     * UPDATE: Merges incremental updates from your business domain into an EXISTING database entity safely.
     */
    @Mapping(target = "userStatusId", ignore = true) // Protect the Primary Key / Constants from accidental mutation
    UserStatusEntity updateEntityFromDomain(UserStatus domain, @MappingTarget UserStatusEntity existingEntity);
}
