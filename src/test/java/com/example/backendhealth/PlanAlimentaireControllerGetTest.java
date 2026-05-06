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
class PlanAlimentaireControllerGetTest {

    @Autowired
    private MockMvc mockMvc;

    // ── GET all plans alimentaires ───────────────────────────────────────────
    @Test
    void getAllPlans_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/plans-alimentaires"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));
    }

    // ── GET plans by user UUID ───────────────────────────────────────────────
    @Test
    void getPlansByUser_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/plans-alimentaires/user/c6903340-7675-4e98-b656-6da142e0fc71"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"));
    }
}
