package com.opao.pp_api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/noaPpLat5Filing")
@Tag(name = "NoaPpLat5Filings", description = "CRUD endpoints for noaPpLat5Filings")
public class NoaPpLat5FilingController {
    private final List<String> noaPpLat5Filings = List.of("noaPpLat5Filing1", "noaPpLat5Filing2");


    public NoaPpLat5FilingController() {

    }

    @GetMapping
    @Operation(summary = "Get all noaPpLat5Filings", description = "Fetches a complete array of all registered noaPpLat5Filings")
    public List<String> getAll() { 
        return new ArrayList<>(noaPpLat5Filings);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find noaPpLat5Filing by ID")
    public ResponseEntity<String> getById(@PathVariable Integer id) { 
        return noaPpLat5Filings.stream()
                .filter(f -> f.equalsIgnoreCase(id.toString()))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Register a brand new noaPpLat5Filing")
    public ResponseEntity<?> create(@Valid @RequestBody Object request) {
        if (request instanceof String newNoaPpLat5Filing && !newNoaPpLat5Filing.isBlank()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newNoaPpLat5Filing);
        }
        return ResponseEntity.badRequest().body("Invalid noaPpLat5Filing");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modify an existing noaPpLat5Filing")
    public ResponseEntity<String> update(@PathVariable Integer id, @Valid @RequestBody Object request) { 
        if (request instanceof String newNoaPpLat5Filing && !newNoaPpLat5Filing.isBlank()) {
            return noaPpLat5Filings.stream()
                    .filter(f -> f.equalsIgnoreCase(id.toString()))
                    .findFirst()
                    .map(f -> ResponseEntity.ok(newNoaPpLat5Filing))
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.badRequest().body("Invalid noaPpLat5Filing");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Wipe a noaPpLat5Filing from the system completely")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (noaPpLat5Filings.stream().anyMatch(f -> f.equalsIgnoreCase(id.toString()))) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    @Operation(summary = "Get the total number of noaPpLat5Filings")
    public ResponseEntity<Long> getNoaPpLat5FilingCount() {
        long count = noaPpLat5Filings.size();
        return ResponseEntity.ok(count);
    }
}
