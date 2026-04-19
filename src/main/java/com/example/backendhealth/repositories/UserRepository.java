package com.example.backendhealth.repositories;

import com.example.backendhealth.entities.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<user, String> {
    Optional<user> findByEmail(String email);
    boolean existsByEmail(String email);
    List<user> findByRole(String role);
}