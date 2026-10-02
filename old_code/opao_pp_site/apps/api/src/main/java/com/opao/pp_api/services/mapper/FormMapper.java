package com.opao.pp_api.services.mapper;

import com.opao.pp_api.repositories.entities.FormEntity;
import com.opao.pp_api.services.domains.Form;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface FormMapper {

    /**
     * READ: Converts a database FormEntity into a clean business Form domain model.
     */
    @Mapping(source = "formId", target = "id")
    @Mapping(source = "formType.formTypeId", target = "formTypeId")
    @Mapping(source = "status.name", target = "statusName") 
    @Mapping(source = "userId.userId", target = "userId") 
    @Mapping(source = ".", target = "hasLineItems", qualifiedByName = "checkHasLineItems")
    Form toDomain(FormEntity entity);

    /**
     * CREATE: Converts a business Form model into a brand-new database FormEntity row.
     */
    @Mapping(source = "id", target = "formId")
    @Mapping(target = "formType", ignore = true) // Set relationships explicitly via the service layer
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "noaPpLat5Collection", ignore = true) // Protect children collections from automatic side-effects
    FormEntity toEntity(Form domain);

    /**
     * UPDATE: Merges incremental updates from your business domain into an existing database row safely.
     */
    @Mapping(target = "formId", ignore = true)              // Protect Database Primary Key from mutation
    @Mapping(target = "formType", ignore = true)            // Managed safely via Service layer lookup
    @Mapping(target = "status", ignore = true)              // Managed safely via Service layer lookup
    @Mapping(target = "userId", ignore = true)              // Managed safely via Service layer lookup
    @Mapping(target = "noaPpLat5Collection", ignore = true) // Prevent accidental child graph erasure
    FormEntity updateEntityFromDomain(Form domain, @MappingTarget FormEntity existingEntity);

    /**
     * Custom translation logic evaluating if the collection relationship contains records.
     */
    @Named("checkHasLineItems")
    default boolean checkHasLineItems(FormEntity entity) {
        if (entity == null) {
            return false;
        }
        return entity.getNoaPpLat5Collection() != null && !entity.getNoaPpLat5Collection().isEmpty();
    }
}
