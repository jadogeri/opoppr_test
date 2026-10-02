package com.opao.pp_api.services.mapper;

import com.opao.pp_api.repositories.entities.UserChangeEntity;
import com.opao.pp_api.services.domains.UserChange;
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
public interface UserChangeMapper {

    /**
     * READ: Converts a database UserChangeEntity into a clean business UserChange domain model.
     */
    @Mapping(source = "userChangeId", target = "id")
    @Mapping(source = "userChangeTypeId.userChangeTypeId", target = "userChangeTypeId")
    @Mapping(source = "userId.userId", target = "userId")
    UserChange toDomain(UserChangeEntity entity);

    /**
     * CREATE: Converts a business UserChange model into a brand-new database UserChangeEntity.
     */
    @Mapping(source = "id", target = "userChangeId")
    @Mapping(target = "userChangeTypeId", ignore = true) // Explicitly map relationships using service layer proxy lookups
    @Mapping(target = "userId", ignore = true)
    UserChangeEntity toEntity(UserChange domain);

    /**
     * UPDATE: Merges incremental updates from your business domain into an EXISTING database entity safely.
     */
    @Mapping(target = "userChangeId", ignore = true) // Protect the Database Primary Key from mutation
    @Mapping(target = "initiatedTime", ignore = true) // Protect initiation audit timestamp from modification updates
    @Mapping(target = "userChangeTypeId", ignore = true) // Handle relational parent codes explicitly via Service layer lookup
    @Mapping(target = "userId", ignore = true)
    UserChangeEntity updateEntityFromDomain(UserChange domain, @MappingTarget UserChangeEntity existingEntity);
}
