package com.example.backendhealth.repositories;

import com.example.backendhealth.entities.Repas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepasRepository extends JpaRepository<Repas, Long> {
}