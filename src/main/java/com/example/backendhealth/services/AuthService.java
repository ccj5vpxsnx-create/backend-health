package com.example.backendhealth.services;

import com.example.backendhealth.dto.LoginDTO;
import com.example.backendhealth.dto.RegisterDTO;
import com.example.backendhealth.entities.user;
import com.example.backendhealth.repositories.UserRepository;
import com.example.backendhealth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Map<String, String> register(RegisterDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        user newUser = new user();
        newUser.setNom(dto.getNom());
        newUser.setPrenom(dto.getPrenom());
        newUser.setEmail(dto.getEmail());
        newUser.setTel(dto.getTel());
        newUser.setRole(dto.getRole() != null ? dto.getRole() : "NUTRITIONIST");
        newUser.setPwd(passwordEncoder.encode(dto.getPwd()));
        newUser.setActive(true);
        userRepository.save(newUser);

        String token = jwtUtil.generateToken(newUser.getEmail(), newUser.getRole());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("email", newUser.getEmail());
        response.put("role", newUser.getRole());
        response.put("typeAbonnement",
                dto.getTypeAbonnement() != null ? dto.getTypeAbonnement() : "MOIS_1"); // ← AJOUTER
        response.put("message", "Inscription réussie !");
        return response;
    }

    public Map<String, String> login(LoginDTO dto) {
        user u = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email introuvable !"));

        if (!passwordEncoder.matches(dto.getPwd(), u.getPwd())) {
            throw new RuntimeException("Mot de passe incorrect !");
        }
        if (!u.isActive()) {
            throw new RuntimeException("Compte désactivé !");
        }

        String token = jwtUtil.generateToken(u.getEmail(), u.getRole());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", u.getRole());
        response.put("nom", u.getNom());
        response.put("prenom", u.getPrenom());
        response.put("message", "Connexion réussie !");
        return response;
    }
}