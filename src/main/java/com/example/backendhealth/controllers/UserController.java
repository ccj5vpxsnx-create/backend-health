package com.example.backendhealth.controllers;

import com.example.backendhealth.entities.Bloomer;
import com.example.backendhealth.entities.user;
import com.example.backendhealth.repositories.BloomerRepository;
import com.example.backendhealth.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;
    private final BloomerRepository bloomerRepository;

    public UserController(UserRepository userRepository, BloomerRepository bloomerRepository) {
        this.userRepository = userRepository;
        this.bloomerRepository = bloomerRepository;
    }

    // GET /api/users/{id} — returns full profile (Bloomer fields if applicable)
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        // Try Bloomer first (has extra fields: age, height, weight, goal)
        Optional<Bloomer> bloomer = bloomerRepository.findById(id);
        if (bloomer.isPresent()) {
            Bloomer b = bloomer.get();
            Map<String, Object> result = new java.util.HashMap<>();
            result.put("id",             b.getId());
            result.put("nom",            b.getNom()            != null ? b.getNom()            : "");
            result.put("prenom",         b.getPrenom()         != null ? b.getPrenom()         : "");
            result.put("email",          b.getEmail()          != null ? b.getEmail()          : "");
            result.put("tel",            b.getTel()            != null ? b.getTel()            : "");
            result.put("role",           b.getRole()           != null ? b.getRole()           : "");
            result.put("age",            b.getAge()            != null ? b.getAge()            : 0);
            result.put("height",         b.getHeight()         != null ? b.getHeight()         : 0.0);
            result.put("weight",         b.getWeight()         != null ? b.getWeight()         : 0.0);
            result.put("goal",           b.getGoal()           != null ? b.getGoal()           : "");
            result.put("lifestyleLevel", b.getLifestyleLevel() != null ? b.getLifestyleLevel() : "");
            return ResponseEntity.ok(result);
        }

        // Fallback to base user
        Optional<user> u = userRepository.findById(id);
        if (u.isPresent()) {
            user usr = u.get();
            Map<String, Object> result = new java.util.HashMap<>();
            result.put("id",     usr.getId());
            result.put("nom",    usr.getNom()    != null ? usr.getNom()    : "");
            result.put("prenom", usr.getPrenom() != null ? usr.getPrenom() : "");
            result.put("email",  usr.getEmail()  != null ? usr.getEmail()  : "");
            result.put("tel",    usr.getTel()    != null ? usr.getTel()    : "");
            result.put("role",   usr.getRole()   != null ? usr.getRole()   : "");
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.notFound().build();
    }
}
