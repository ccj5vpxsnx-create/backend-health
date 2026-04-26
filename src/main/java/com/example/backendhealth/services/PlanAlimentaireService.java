package com.example.backendhealth.services;

import com.example.backendhealth.dto.PlanAlimentaireDTO;
import com.example.backendhealth.entities.PlanAlimentaire;
import com.example.backendhealth.repositories.PlanAlimentaireRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanAlimentaireService {

    private final PlanAlimentaireRepository planRepo;

    public PlanAlimentaireService(PlanAlimentaireRepository planRepo) {
        this.planRepo = planRepo;
    }

    public List<PlanAlimentaireDTO> getAllPlans() {
        return planRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PlanAlimentaireDTO getPlanById(Long id) {
        PlanAlimentaire plan = planRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plan introuvable : id=" + id));
        return toDTO(plan);
    }

    public List<PlanAlimentaireDTO> getPlansByUserId(Long userId) {
        return planRepo.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PlanAlimentaireDTO> getPlansByRegimeId(Long regimeId) {
        return planRepo.findByRegimeId(regimeId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PlanAlimentaireDTO createPlan(PlanAlimentaireDTO dto) {
        PlanAlimentaire plan = toEntity(dto);
        return toDTO(planRepo.save(plan));
    }

    public PlanAlimentaireDTO updatePlan(Long id, PlanAlimentaireDTO dto) {
        PlanAlimentaire existing = planRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plan introuvable : id=" + id));

        existing.setNom(dto.getNom());
        existing.setDescription(dto.getDescription());
        existing.setDateDebut(dto.getDateDebut());
        existing.setDateFin(dto.getDateFin());
        existing.setCaloriesJournalieres(dto.getCaloriesJournalieres());
        existing.setObjectif(dto.getObjectif());
        existing.setUserId(dto.getUserId());
        existing.setRegimeId(dto.getRegimeId());

        return toDTO(planRepo.save(existing));
    }

    public void deletePlan(Long id) {
        if (!planRepo.existsById(id)) {
            throw new EntityNotFoundException("Plan introuvable : id=" + id);
        }
        planRepo.deleteById(id);
    }

    private PlanAlimentaireDTO toDTO(PlanAlimentaire plan) {
        PlanAlimentaireDTO dto = new PlanAlimentaireDTO();
        dto.setId(plan.getId());
        dto.setNom(plan.getNom());
        dto.setDescription(plan.getDescription());
        dto.setDateDebut(plan.getDateDebut());
        dto.setDateFin(plan.getDateFin());
        dto.setCaloriesJournalieres(plan.getCaloriesJournalieres());
        dto.setObjectif(plan.getObjectif());
        dto.setUserId(plan.getUserId());
        dto.setRegimeId(plan.getRegimeId());
        return dto;
    }

    private PlanAlimentaire toEntity(PlanAlimentaireDTO dto) {
        PlanAlimentaire plan = new PlanAlimentaire();
        plan.setNom(dto.getNom());
        plan.setDescription(dto.getDescription());
        plan.setDateDebut(dto.getDateDebut());
        plan.setDateFin(dto.getDateFin());
        plan.setCaloriesJournalieres(dto.getCaloriesJournalieres());
        plan.setObjectif(dto.getObjectif());
        plan.setUserId(dto.getUserId());
        plan.setRegimeId(dto.getRegimeId());
        return plan;
    }
}