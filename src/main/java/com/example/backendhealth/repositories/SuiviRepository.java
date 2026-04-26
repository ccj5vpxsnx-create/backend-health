package com.example.backendhealth.repositories;

import com.example.backendhealth.entities.Suivi_quotidien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuiviRepository extends JpaRepository<Suivi_quotidien,Integer> {
}
