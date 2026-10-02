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
@RequestMapping("/api/userChange")
@Tag(name = "UserChanges", description = "CRUD endpoints for userChanges")
public class UserChangeController {
    private final List<String> userChanges = List.of("userChange1", "userChange2   ");


    public UserChangeController() {

    }

    @GetMapping
    @Operation(summary = "Get all userChanges", description = "Fetches a complete array of all registered userChanges")
    public List<String> getAll() { 
        return new ArrayList<>(userChanges);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find userChange by ID")
    public ResponseEntity<String> getById(@PathVariable Integer id) { 
        return userChanges.stream()
                .filter(f -> f.equalsIgnoreCase(id.toString()))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Register a brand new userChange")
    public ResponseEntity<?> create(@Valid @RequestBody Object request) {
        if (request instanceof String newUserChange && !newUserChange.isBlank()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newUserChange);
        }
        return ResponseEntity.badRequest().body("Invalid userChange");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modify an existing userChange")
    public ResponseEntity<String> update(@PathVariable Integer id, @Valid @RequestBody Object request) { 
        if (request instanceof String newUserChange && !newUserChange.isBlank()) {
            return userChanges.stream()
                    .filter(f -> f.equalsIgnoreCase(id.toString()))
                    .findFirst()
                    .map(f -> ResponseEntity.ok(newUserChange))
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.badRequest().body("Invalid userChange");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Wipe a userChange from the system completely")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (userChanges.stream().anyMatch(f -> f.equalsIgnoreCase(id.toString()))) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    @Operation(summary = "Get the total number of userChanges")
    public ResponseEntity<Long> getUserChangeCount() {
        long count = userChanges.size();
        return ResponseEntity.ok(count);
    }
}
