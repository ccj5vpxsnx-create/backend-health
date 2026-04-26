package com.example.backendhealth.services;

import com.example.backendhealth.dto.ExerciceDto;
import com.example.backendhealth.dto.PlanExerciceDto;
import com.example.backendhealth.entities.PlanExercice;
import com.example.backendhealth.repositories.PlanExerciceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanExerciceService {

    private final PlanExerciceRepository planExerciceRepository;
    private final ExerciceService exerciceService;

    public List<PlanExerciceDto> getAll() {
        return planExerciceRepository.findAll()
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public PlanExerciceDto getById(Long id) {
        PlanExercice plan = planExerciceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan non trouvé avec l'id : " + id));
        return toDto(plan);
    }

    public PlanExerciceDto create(PlanExerciceDto dto) {
        return toDto(planExerciceRepository.save(toEntity(dto)));
    }

    public PlanExerciceDto update(Long id, PlanExerciceDto dto) {
        PlanExercice plan = planExerciceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan non trouvé avec l'id : " + id));

        plan.setNom(dto.getNom());
        plan.setDescription(dto.getDescription());
        plan.setDureeSemaines(dto.getDureeSemaines());
        plan.setSeancesParSemaine(dto.getSeancesParSemaine());
        plan.setActif(dto.getActif());
        plan.setDateDebut(dto.getDateDebut());
        plan.setDateFin(dto.getDateFin());

        return toDto(planExerciceRepository.save(plan));
    }

    public void delete(Long id) {
        if (!planExerciceRepository.existsById(id))
            throw new RuntimeException("Plan non trouvé avec l'id : " + id);
        planExerciceRepository.deleteById(id);
    }

    private PlanExerciceDto toDto(PlanExercice p) {
        List<ExerciceDto> exerciceDtos = p.getExercices()
                .stream().map(exerciceService::toDto).collect(Collectors.toList());

        return PlanExerciceDto.builder()
                .id(p.getId())
                .nom(p.getNom())
                .description(p.getDescription())
                .dureeSemaines(p.getDureeSemaines())
                .seancesParSemaine(p.getSeancesParSemaine())
                .actif(p.getActif())
                .dateDebut(p.getDateDebut())
                .dateFin(p.getDateFin())
                .exercices(exerciceDtos)
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }

    private PlanExercice toEntity(PlanExerciceDto dto) {
        return PlanExercice.builder()
                .nom(dto.getNom())
                .description(dto.getDescription())
                .dureeSemaines(dto.getDureeSemaines())
                .seancesParSemaine(dto.getSeancesParSemaine())
                .actif(dto.getActif() != null ? dto.getActif() : true)
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .build();
    }
}