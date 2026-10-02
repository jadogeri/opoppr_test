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
@RequestMapping("/api/noaPpLat5Inventories")
@Tag(name = "NoaPpLat5Inventories", description = "CRUD endpoints for noaPpLat5Inventoriess")
public class NoaPpLat5InventoriesController {
    private final List<String> noaPpLat5Inventoriess = List.of("noaPpLat5Inventories1", "noaPpLat5Inventories2");


    public NoaPpLat5InventoriesController() {

    }

    @GetMapping
    @Operation(summary = "Get all noaPpLat5Inventoriess", description = "Fetches a complete array of all registered noaPpLat5Inventoriess")
    public List<String> getAll() { 
        return new ArrayList<>(noaPpLat5Inventoriess);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find noaPpLat5Inventories by ID")
    public ResponseEntity<String> getById(@PathVariable Integer id) { 
        return noaPpLat5Inventoriess.stream()
                .filter(f -> f.equalsIgnoreCase(id.toString()))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Register a brand new noaPpLat5Inventories")
    public ResponseEntity<?> create(@Valid @RequestBody Object request) {
        if (request instanceof String newNoaPpLat5Inventories && !newNoaPpLat5Inventories.isBlank()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newNoaPpLat5Inventories);
        }
        return ResponseEntity.badRequest().body("Invalid noaPpLat5Inventories");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modify an existing noaPpLat5Inventories")
    public ResponseEntity<String> update(@PathVariable Integer id, @Valid @RequestBody Object request) { 
        if (request instanceof String newNoaPpLat5Inventories && !newNoaPpLat5Inventories.isBlank()) {
            return noaPpLat5Inventoriess.stream()
                    .filter(f -> f.equalsIgnoreCase(id.toString()))
                    .findFirst()
                    .map(f -> ResponseEntity.ok(newNoaPpLat5Inventories))
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.badRequest().body("Invalid noaPpLat5Inventories");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Wipe a noaPpLat5Inventories from the system completely")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (noaPpLat5Inventoriess.stream().anyMatch(f -> f.equalsIgnoreCase(id.toString()))) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    @Operation(summary = "Get the total number of noaPpLat5Inventoriess")
    public ResponseEntity<Long> getNoaPpLat5InventoriesCount() {
        long count = noaPpLat5Inventoriess.size();
        return ResponseEntity.ok(count);
    }
}
