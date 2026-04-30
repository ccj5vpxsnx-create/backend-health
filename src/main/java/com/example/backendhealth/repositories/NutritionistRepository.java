// NutritionistRepository
package com.example.backendhealth.repositories;

import com.example.backendhealth.entities.Nutritionist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NutritionistRepository extends JpaRepository<Nutritionist, String> {
    Optional<Nutritionist> findByEmail(String email);

}