package com.example.backendhealth.controllers;

import com.example.backendhealth.dto.PlanAlimentaireDTO;
import com.example.backendhealth.services.PlanAlimentaireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans-alimentaires")
@CrossOrigin(origins = "*")
public class PlanAlimentaireController {

    private final PlanAlimentaireService planAlimentaireService;

    public PlanAlimentaireController(PlanAlimentaireService planAlimentaireService) {
        this.planAlimentaireService = planAlimentaireService;
    }

    @GetMapping
    public ResponseEntity<List<PlanAlimentaireDTO>> getAllPlans() {
        List<PlanAlimentaireDTO> plans = planAlimentaireService.getAllPlans();
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanAlimentaireDTO> getPlanById(@PathVariable Long id) {
        PlanAlimentaireDTO plan = planAlimentaireService.getPlanById(id);
        return ResponseEntity.ok(plan);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PlanAlimentaireDTO>> getPlansByUser(@PathVariable Long userId) {
        List<PlanAlimentaireDTO> plans = planAlimentaireService.getPlansByUserId(userId);
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/regime/{regimeId}")
    public ResponseEntity<List<PlanAlimentaireDTO>> getPlansByRegime(@PathVariable Long regimeId) {
        List<PlanAlimentaireDTO> plans = planAlimentaireService.getPlansByRegimeId(regimeId);
        return ResponseEntity.ok(plans);
    }

    @PostMapping
    public ResponseEntity<PlanAlimentaireDTO> createPlan(@RequestBody PlanAlimentaireDTO planDTO) {
        PlanAlimentaireDTO created = planAlimentaireService.createPlan(planDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanAlimentaireDTO> updatePlan(
            @PathVariable Long id,
            @RequestBody PlanAlimentaireDTO planDTO) {
        PlanAlimentaireDTO updated = planAlimentaireService.updatePlan(id, planDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        planAlimentaireService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }
}