package com.example.backendhealth.repositories;

import com.example.backendhealth.entities.PlanAlimentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanAlimentaireRepository extends JpaRepository<PlanAlimentaire, Long> {

    List<PlanAlimentaire> findByUserId(Long userId);

    List<PlanAlimentaire> findByRegimeId(Long regimeId);
}