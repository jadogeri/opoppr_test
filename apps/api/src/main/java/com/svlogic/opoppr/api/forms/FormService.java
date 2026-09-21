package com.svlogic.opoppr.api.forms;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.svlogic.opoppr.api.forms.FormModels.*;

@Service
public class FormService {
    private final AtomicLong rowIds = new AtomicLong(100);
    private final Map<Long, FormSummary> forms = new LinkedHashMap<>();
    private final Map<Long, List<Lat5Row>> rowsByForm = new ConcurrentHashMap<>();

    public FormService() {
        forms.put(1L, new FormSummary(1, "2025 Personal Property Return", 2025,
                "DEMO-0001", "IN_PROGRESS", Instant.now()));
        forms.put(2L, new FormSummary(2, "2024 Personal Property Return", 2024,
                "DEMO-0001", "SUBMITTED", Instant.parse("2025-03-01T15:00:00Z")));
        rowsByForm.put(1L, new ArrayList<>(List.of(
                new Lat5Row(1L, 1, "10", "Furniture", "Office furniture", 125000L, 110000L),
                new Lat5Row(2L, 1, "20", "Computer equipment", "Workstations", 240000L, 225000L),
                new Lat5Row(3L, 2, "30", "Machinery", "Production equipment", 500000L, 480000L)
        )));
    }

    public DashboardSummary dashboard(String displayName) {
        Map<String, Long> counts = forms.values().stream()
                .collect(Collectors.groupingBy(FormSummary::status, LinkedHashMap::new, Collectors.counting()));
        return new DashboardSummary(displayName, List.copyOf(forms.values()), counts);
    }

    public Lat5Form getLat5(long formId) {
        FormSummary form = requireForm(formId);
        Map<Integer, List<Lat5Row>> sections = new LinkedHashMap<>();
        for (int section = 1; section <= 5; section++) {
            int currentSection = section;
            sections.put(section, rowsByForm.getOrDefault(formId, List.of()).stream()
                    .filter(row -> row.section() == currentSection)
                    .toList());
        }
        return new Lat5Form(form.id(), form.filingYear(), "Demo Taxpayer", "taxpayer@example.test", sections);
    }

    public Lat5Form updateLat5(long formId, Lat5UpdateRequest request) {
        requireForm(formId);
        List<Lat5Row> normalized = request.rows().stream()
                .map(row -> row.id() == null
                        ? new Lat5Row(rowIds.incrementAndGet(), row.section(), row.category(),
                        row.propertyType(), row.description(), row.acquisitionCost(), row.priorYearCost())
                        : row)
                .toList();
        rowsByForm.put(formId, new ArrayList<>(normalized));
        FormSummary previous = forms.get(formId);
        forms.put(formId, new FormSummary(previous.id(), previous.title(), previous.filingYear(),
                previous.billNumber(), "IN_PROGRESS", Instant.now()));
        return getLat5(formId);
    }

    public FormSummary submit(long formId) {
        FormSummary previous = requireForm(formId);
        FormSummary submitted = new FormSummary(previous.id(), previous.title(), previous.filingYear(),
                previous.billNumber(), "SUBMITTED", Instant.now());
        forms.put(formId, submitted);
        return submitted;
    }

    private FormSummary requireForm(long formId) {
        FormSummary form = forms.get(formId);
        if (form == null) {
            throw new FormNotFoundException(formId);
        }
        return form;
    }

    public static class FormNotFoundException extends RuntimeException {
        public FormNotFoundException(long formId) {
            super("Form " + formId + " was not found");
        }
    }
}