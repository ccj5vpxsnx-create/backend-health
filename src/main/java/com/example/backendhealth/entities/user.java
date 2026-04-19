package com.example.backendhealth.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nom;
    private String prenom;
    private String email;
    private String pwd;
    private String tel;
    private String role; // ADHERENT, CLIENT, PATIENT, NUTRITIONNISTE, COACH
    private boolean active;
}