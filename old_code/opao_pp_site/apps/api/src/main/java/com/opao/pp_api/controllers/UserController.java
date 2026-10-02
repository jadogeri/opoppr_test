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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "CRUD endpoints for administrative system users")
public class UserController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    public UserController(UserService userService, UserDtoMapper userDtoMapper) {
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Fetches a complete array of all registered system profiles")
    public List<UserRegistrationResponse> getAll() { 
        return userService.getAllUsers().stream()
                .map(userDtoMapper::toRegistrationResponse) 
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find user by ID")
    public ResponseEntity<UserRegistrationResponse> getById(@PathVariable Integer id) { 
        return userService.getUserById(id)
                .map(userDtoMapper::toRegistrationResponse) 
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Register a brand new user profile")
    public ResponseEntity<?> create(@Valid @RequestBody UserRegistrationRequest request) {
        try {
            User domainModel = userDtoMapper.toDomain(request);
            User savedDomain = userService.createUser(domainModel);
            UserRegistrationResponse response = userDtoMapper.toRegistrationResponse(savedDomain); 
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Modify an existing profile context")
    public ResponseEntity<UserUpdateResponse> update(@PathVariable Integer id, @Valid @RequestBody UserUpdateRequest request) { 
        // 🚀 Pass the request object directly to the service layer
        User domainUpdateData = userDtoMapper.toDomain(request); 
        return userService.updateUser(id, domainUpdateData) 
                .map(userDtoMapper::toUpdateResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Wipe a user record from the system completely")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    @Operation(summary = "Get the total number of users")
    public ResponseEntity<Long> getUserCount() {
        long count = userService.getAllUsers().size();
        return ResponseEntity.ok(count);
    }
}
