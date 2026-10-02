package com.opao.pp_api.controllers;

import com.opao.pp_api.controllers.dto.request.UserRegistrationRequest;
import com.opao.pp_api.controllers.dto.request.UserUpdateRequest;
import com.opao.pp_api.controllers.dto.response.UserRegistrationResponse;
import com.opao.pp_api.controllers.dto.response.UserUpdateResponse;
import com.opao.pp_api.controllers.mapper.UserDtoMapper;
import com.opao.pp_api.services.UserService;
import com.opao.pp_api.services.domains.User;

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
@RequestMapping("/api/forms")
@Tag(name = "Forms", description = "CRUD endpoints for forms")
public class FormController {
    private final List<String> forms = List.of("form1", "form2");


    public FormController() {

    }

    @GetMapping
    @Operation(summary = "Get all forms", description = "Fetches a complete array of all registered forms")
    public List<String> getAll() { 
        return new ArrayList<>(forms);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find form by ID")
    public ResponseEntity<String> getById(@PathVariable Integer id) { 
        return forms.stream()
                .filter(f -> f.equalsIgnoreCase(id.toString()))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Register a brand new form")
    public ResponseEntity<?> create(@Valid @RequestBody Object request) {
        if (request instanceof String newForm && !newForm.isBlank()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newForm);
        }
        return ResponseEntity.badRequest().body("Invalid form");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modify an existing form")
    public ResponseEntity<String> update(@PathVariable Integer id, @Valid @RequestBody Object request) { 
        if (request instanceof String newForm && !newForm.isBlank()) {
            return forms.stream()
                    .filter(f -> f.equalsIgnoreCase(id.toString()))
                    .findFirst()
                    .map(f -> ResponseEntity.ok(newForm))
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.badRequest().body("Invalid form");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Wipe a form from the system completely")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (forms.stream().anyMatch(f -> f.equalsIgnoreCase(id.toString()))) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    @Operation(summary = "Get the total number of forms")
    public ResponseEntity<Long> getFormCount() {
        long count = forms.size();
        return ResponseEntity.ok(count);
    }
}
