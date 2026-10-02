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
@RequestMapping("/api/propertyAsset")
@Tag(name = "PropertyAssets", description = "CRUD endpoints for propertyAssets")
public class PropertyAssetController {
    private final List<String> propertyAssets = List.of("propertyAsset1", "propertyAsset2   ");


    public PropertyAssetController() {

    }

    @GetMapping
    @Operation(summary = "Get all propertyAssets", description = "Fetches a complete array of all registered propertyAssets")
    public List<String> getAll() { 
        return new ArrayList<>(propertyAssets);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find propertyAsset by ID")
    public ResponseEntity<String> getById(@PathVariable Integer id) { 
        return propertyAssets.stream()
                .filter(f -> f.equalsIgnoreCase(id.toString()))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Register a brand new propertyAsset")
    public ResponseEntity<?> create(@Valid @RequestBody Object request) {
        if (request instanceof String newPropertyAsset && !newPropertyAsset.isBlank()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newPropertyAsset);
        }
        return ResponseEntity.badRequest().body("Invalid propertyAsset");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modify an existing propertyAsset")
    public ResponseEntity<String> update(@PathVariable Integer id, @Valid @RequestBody Object request) { 
        if (request instanceof String newPropertyAsset && !newPropertyAsset.isBlank()) {
            return propertyAssets.stream()
                    .filter(f -> f.equalsIgnoreCase(id.toString()))
                    .findFirst()
                    .map(f -> ResponseEntity.ok(newPropertyAsset))
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.badRequest().body("Invalid propertyAsset");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Wipe a propertyAsset from the system completely")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (propertyAssets.stream().anyMatch(f -> f.equalsIgnoreCase(id.toString()))) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    @Operation(summary = "Get the total number of propertyAssets")
    public ResponseEntity<Long> getPropertyAssetCount() {
        long count = propertyAssets.size();
        return ResponseEntity.ok(count);
    }
}
