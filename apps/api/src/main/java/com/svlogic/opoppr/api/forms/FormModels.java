package com.svlogic.opoppr.api.forms;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class FormModels {
    private FormModels() {
    }

    public record FormSummary(
            long id,
            String title,
            int filingYear,
            String billNumber,
            String status,
            Instant lastModifiedDate
    ) {
    }

    public record DashboardSummary(
            String displayName,
            List<FormSummary> forms,
            Map<String, Long> counts
    ) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record Lat5Row(
            Long id,
            @Min(1) @Max(5) int section,
            @NotBlank String category,
            @NotBlank String propertyType,
            String description,
            @DecimalMin("0.0") Long acquisitionCost,
            @DecimalMin("0.0") Long priorYearCost
    ) {
    }

    public record Lat5Form(
            long formId,
            int filingYear,
            String ownerName,
            String contactEmail,
            Map<Integer, List<Lat5Row>> sections
    ) {
    }

    public record Lat5UpdateRequest(
            @NotEmpty @Size(max = 500) List<@Valid Lat5Row> rows
    ) {
    }
}