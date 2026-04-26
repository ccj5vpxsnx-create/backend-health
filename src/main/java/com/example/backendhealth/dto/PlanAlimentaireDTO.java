package com.example.backendhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanAlimentaireDTO {

    private Long id;
    private String nom;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Integer caloriesJournalieres;
    private String objectif;
    private Long userId;
    private Long regimeId;

    private List<RepasDTO> repas;
}