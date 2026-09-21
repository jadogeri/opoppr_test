package com.svlogic.opoppr.api.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

@Service
public class TokenService {
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private final byte[] secret;
    private final Duration ttl;

    public TokenService(
            @Value("${opoppr.jwt.secret}") String secret,
            @Value("${opoppr.jwt.ttl:PT8H}") Duration ttl
    ) {
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        this.ttl = ttl;
    }

    public String issue(String username, String role) {
        long expiresAt = Instant.now().plus(ttl).getEpochSecond();
        String header = encode("{\"alg\":\"HS256\",\"typ\":\"OPP\"}");
        String payload = encode("{\"sub\":\"" + escape(username) + "\",\"role\":\"" + escape(role)
                + "\",\"exp\":" + expiresAt + "}");
        return header + "." + payload + "." + sign(header + "." + payload);
    }

    public Claims parse(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3 || !parts[2].equals(sign(parts[0] + "." + parts[1]))) {
                return null;
            }
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            String subject = value(payload, "sub");
            String role = value(payload, "role");
            long expiry = Long.parseLong(number(payload, "exp"));
            if (subject == null || role == null || Instant.now().getEpochSecond() >= expiry) {
                return null;
            }
            return new Claims(subject, role, Instant.ofEpochSecond(expiry));
        } catch (RuntimeException exception) {
            return null;
        }
    }

    private String sign(String value) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(secret, HMAC_ALGORITHM));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(
                    mac.doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to sign access token", exception);
        }
    }

    private String encode(String value) {
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private String value(String json, String key) {
        var matcher = java.util.regex.Pattern
                .compile("\"" + key + "\":\"([^\"]+)\"")
                .matcher(json);
        return matcher.find() ? matcher.group(1) : null;
    }

    private String number(String json, String key) {
        var matcher = java.util.regex.Pattern
                .compile("\"" + key + "\":(\\d+)")
                .matcher(json);
        return matcher.find() ? matcher.group(1) : null;
    }

    public record Claims(String subject, String role, Instant expiresAt) {
    }
}