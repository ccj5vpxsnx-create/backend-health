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
class ConversationControllerGetTest {

    @Autowired
    private MockMvc mockMvc;

    // ── GET conversations by patient UUID ────────────────────────────────────
    @Test
    void getConversationsByPatient_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/conversations/patient/c6903340-7675-4e98-b656-6da142e0fc71"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));
    }

    // ── GET conversations by nutritionniste UUID ─────────────────────────────
    @Test
    void getConversationsByNutritionniste_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/conversations/nutritionist/817b8f71-b294-4e09-a55e-b8aca3878f63"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));
    }

    // ── GET conversations by coach UUID ──────────────────────────────────────
    @Test
    void getConversationsByCoach_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/conversations/coach/9275420d-85b9-41c5-a0f9-c7ac670fdba6"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));
    }

    // ── GET conversation by ID ───────────────────────────────────────────────
    @Test
    void getConversationById_existing_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/conversations/26"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(26))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    // ── GET conversation by ID — non-existent ────────────────────────────────
    @Test
    void getConversationById_nonExistent_shouldReturn500OrNotFound() throws Exception {
        mockMvc.perform(get("/api/conversations/99999"))
                .andExpect(status().is5xxServerError());
    }
}
