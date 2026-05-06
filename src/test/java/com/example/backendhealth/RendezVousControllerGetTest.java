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
class RendezVousControllerGetTest {

    @Autowired
    private MockMvc mockMvc;

    // ── GET all rendez-vous ──────────────────────────────────────────────────
    @Test
    void getAllRendezVous_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/rendez-vous"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));
    }

    // ── GET all nutritionnistes ──────────────────────────────────────────────
    @Test
    void getAllNutritionnistes_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/rendez-vous/nutritionnistes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));
    }

    // ── GET all coachs ───────────────────────────────────────────────────────
    @Test
    void getAllCoachs_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/rendez-vous/coachs"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));
    }

    // ── GET rdv by patient UUID ──────────────────────────────────────────────
    @Test
    void getRdvByPatient_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/rendez-vous/user/c6903340-7675-4e98-b656-6da142e0fc71"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));
    }

    // ── GET rdv by nutritionniste UUID ───────────────────────────────────────
    @Test
    void getRdvByNutritionniste_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/rendez-vous/nutritionniste/817b8f71-b294-4e09-a55e-b8aca3878f63"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));
    }

    // ── GET rdv by coach UUID ────────────────────────────────────────────────
    @Test
    void getRdvByCoach_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/rendez-vous/coach/9275420d-85b9-41c5-a0f9-c7ac670fdba6"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));
    }

    // ── GET rdv by statut ────────────────────────────────────────────────────
    @Test
    void getRdvByStatut_EN_ATTENTE_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/rendez-vous/statut/EN_ATTENTE"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));
    }

    @Test
    void getRdvByStatut_CONFIRME_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/rendez-vous/statut/CONFIRME"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));
    }
}
