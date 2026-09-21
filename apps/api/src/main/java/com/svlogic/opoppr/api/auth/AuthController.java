package com.svlogic.opoppr.api.auth;

import com.svlogic.opoppr.api.security.TokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.billNumber(), request.pin()));
        return ResponseEntity.ok(new LoginResponse(
                tokenService.issue(authentication.getName(), "ADMIN"),
                authentication.getName(),
                "ADMIN"));
    }

    public record LoginRequest(
            @NotBlank(message = "Bill number is required") String billNumber,
            @NotBlank(message = "PIN is required") String pin
    ) {
    }

    public record LoginResponse(String accessToken, String username, String role) {
    }
}