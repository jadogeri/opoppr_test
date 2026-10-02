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
@RequestMapping("/api/noaPpLat5")
@Tag(name = "NoaPpLat5s", description = "CRUD endpoints for noaPpLat5s")
public class NoaPpLat5Controller {
    private final List<String> noaPpLat5s = List.of("noaPpLat51", "noaPpLat52");


    public NoaPpLat5Controller() {

    }

    @GetMapping
    @Operation(summary = "Get all noaPpLat5s", description = "Fetches a complete array of all registered noaPpLat5s")
    public List<String> getAll() { 
        return new ArrayList<>(noaPpLat5s);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find noaPpLat5 by ID")
    public ResponseEntity<String> getById(@PathVariable Integer id) { 
        return noaPpLat5s.stream()
                .filter(f -> f.equalsIgnoreCase(id.toString()))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Register a brand new noaPpLat5")
    public ResponseEntity<?> create(@Valid @RequestBody Object request) {
        if (request instanceof String newNoaPpLat5 && !newNoaPpLat5.isBlank()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newNoaPpLat5);
        }
        return ResponseEntity.badRequest().body("Invalid noaPpLat5");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modify an existing noaPpLat5")
    public ResponseEntity<String> update(@PathVariable Integer id, @Valid @RequestBody Object request) { 
        if (request instanceof String newNoaPpLat5 && !newNoaPpLat5.isBlank()) {
            return noaPpLat5s.stream()
                    .filter(f -> f.equalsIgnoreCase(id.toString()))
                    .findFirst()
                    .map(f -> ResponseEntity.ok(newNoaPpLat5))
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.badRequest().body("Invalid noaPpLat5");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Wipe a noaPpLat5 from the system completely")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (noaPpLat5s.stream().anyMatch(f -> f.equalsIgnoreCase(id.toString()))) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    @Operation(summary = "Get the total number of noaPpLat5s")
    public ResponseEntity<Long> getNoaPpLat5Count() {
        long count = noaPpLat5s.size();
        return ResponseEntity.ok(count);
    }
}
