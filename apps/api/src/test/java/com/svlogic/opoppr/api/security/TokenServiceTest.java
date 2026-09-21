package com.svlogic.opoppr.api.security;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class TokenServiceTest {
    private final TokenService tokenService = new TokenService(
            "a-test-secret-that-is-long-enough",
            Duration.ofHours(1));

    @Test
    void issuesAndParsesSignedToken() {
        String token = tokenService.issue("OPAADMIN", "ADMIN");

        TokenService.Claims claims = tokenService.parse(token);

        assertThat(claims).isNotNull();
        assertThat(claims.subject()).isEqualTo("OPAADMIN");
        assertThat(claims.role()).isEqualTo("ADMIN");
    }

    @Test
    void rejectsTamperedToken() {
        String token = tokenService.issue("OPAADMIN", "ADMIN");

        assertThat(tokenService.parse(token + "tampered")).isNull();
    }
}