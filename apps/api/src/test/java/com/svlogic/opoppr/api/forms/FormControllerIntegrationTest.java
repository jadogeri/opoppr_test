package com.svlogic.opoppr.api.forms;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FormControllerIntegrationTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    private String token() throws Exception {
        return objectMapper.readTree(mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"billNumber":"OPAADMIN","pin":"123456"}
                                """))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()).get("accessToken").asText();
    }

    @Test
    void readsAndUpdatesLat5RowsThroughVersionedApi() throws Exception {
        String token = token();

        mockMvc.perform(get("/api/v1/forms/1/lat5").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.formId").value(1))
                .andExpect(jsonPath("$.sections['1'][0].propertyType").value("Furniture"));

        mockMvc.perform(put("/api/v1/forms/1/lat5")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("rows", java.util.List.of(
                                Map.of("section", 1, "category", "10", "propertyType", "Furniture",
                                        "description", "Updated", "acquisitionCost", 150000)
                        )))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sections['1'][0].description").value("Updated"));
    }

    @Test
    void protectsDashboardWithoutToken() throws Exception {
        mockMvc.perform(get("/api/v1/dashboard"))
                .andExpect(status().isUnauthorized());
    }
}