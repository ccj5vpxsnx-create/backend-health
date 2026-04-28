// BloomerRepository
package com.example.backendhealth.repositories;

import com.example.backendhealth.entities.Bloomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloomerRepository extends JpaRepository<Bloomer, String> {}