package com.example.backendhealth.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String nom;
    private String prenom;
    private String email;
    private String pwd;
    private String tel;
    private String role;
    private String typeAbonnement; // ← AJOUTER
}