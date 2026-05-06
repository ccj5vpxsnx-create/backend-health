package com.example.backendhealth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerGetTest {

    @Autowired
    private MockMvc mockMvc;

    // ── GET user profile by UUID (Ghada) ─────────────────────────────────────
    @Test
    void getUserById_existingBloomer_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/users/c6903340-7675-4e98-b656-6da142e0fc71"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.prenom").value("Ghada"))
                .andExpect(jsonPath("$.nom").value("Aloui"))
                .andExpect(jsonPath("$.role").value("BLOOMER"))
                .andExpect(jsonPath("$.weight").value(67.0))
                .andExpect(jsonPath("$.height").value(166.0))
                .andExpect(jsonPath("$.goal").value("Muscle Gain"));
    }

    // ── GET user profile — non-existent UUID ─────────────────────────────────
    @Test
    void getUserById_nonExistent_shouldReturn404() throws Exception {
        mockMvc.perform(get("/api/users/00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isNotFound());
    }

    // ── GET coach profile by UUID (Karim) ────────────────────────────────────
    @Test
    void getUserById_existingCoach_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/users/9275420d-85b9-41c5-a0f9-c7ac670fdba6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prenom").value("Karim"))
                .andExpect(jsonPath("$.role").value("COACH"));
    }
}
