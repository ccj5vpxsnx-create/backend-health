// CoachRepository
package com.example.backendhealth.repositories;

import com.example.backendhealth.entities.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, String> {
    Optional<Coach> findByEmail(String email);

}