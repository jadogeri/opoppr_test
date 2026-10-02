package com.opao.pp_api.controllers.dto.request;

import com.opao.pp_api.common.constants.models.BusinessTypeConstants;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size; 

public record BusinessTypeRequest(
    @NotNull Integer code,

    @Size(min = BusinessTypeConstants.BUSINESS_DESCRIPTION_MIN_LENGTH, max = BusinessTypeConstants.BUSINESS_DESCRIPTION_MAX_LENGTH)
    @NotNull String description
) {}
