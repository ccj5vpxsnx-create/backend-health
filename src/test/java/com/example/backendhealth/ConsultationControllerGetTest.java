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
class ConsultationControllerGetTest {

    @Autowired
    private MockMvc mockMvc;

    // ── GET all consultations ────────────────────────────────────────────────
    @Test
    void getAllConsultations_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/consultations"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));
    }

    // ── GET consultations by nutritionniste UUID ─────────────────────────────
    @Test
    void getConsultationsByNutritionniste_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/consultations/nutritionniste/817b8f71-b294-4e09-a55e-b8aca3878f63"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));
    }
}
