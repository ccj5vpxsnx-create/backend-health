package com.example.backendhealth.services;

import com.example.backendhealth.dto.LoginDTO;
import com.example.backendhealth.dto.RegisterDTO;
import com.example.backendhealth.entities.Bloomer;
import com.example.backendhealth.entities.Coach;
import com.example.backendhealth.entities.Nutritionist;
import com.example.backendhealth.entities.user;
import com.example.backendhealth.repositories.BloomerRepository;
import com.example.backendhealth.repositories.CoachRepository;
import com.example.backendhealth.repositories.NutritionistRepository;
import com.example.backendhealth.repositories.UserRepository;
import com.example.backendhealth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired private UserRepository userRepository;
    @Autowired private NutritionistRepository nutritionistRepository;
    @Autowired private CoachRepository coachRepository;
    @Autowired private BloomerRepository bloomerRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    public Map<String, String> register(RegisterDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        String role = dto.getRole() != null ? dto.getRole().toUpperCase() : "BLOOMER";
        String token = null;
        Map<String, String> response = new HashMap<>();

        switch (role) {
            case "NUTRITIONIST": {
                Nutritionist n = new Nutritionist();
                n.setNom(dto.getNom());
                n.setPrenom(dto.getPrenom());
                n.setEmail(dto.getEmail());
                n.setTel(dto.getTel());
                n.setPwd(passwordEncoder.encode(dto.getPwd()));
                n.setActive(true);
                n.setRole(role);
                n.setSpecialite(dto.getSpecialite());
                n.setLocalisation(dto.getLocalisation());
                n.setTypeAbonnement(
                        dto.getTypeAbonnement() != null ? dto.getTypeAbonnement() : "MOIS_1"
                );
                nutritionistRepository.save(n);
                token = jwtUtil.generateToken(n.getEmail(), n.getRole());
                response.put("typeAbonnement", n.getTypeAbonnement());
                break;
            }
            case "COACH": {
                Coach c = new Coach();
                c.setNom(dto.getNom());
                c.setPrenom(dto.getPrenom());
                c.setEmail(dto.getEmail());
                c.setTel(dto.getTel());
                c.setPwd(passwordEncoder.encode(dto.getPwd()));
                c.setActive(true);
                c.setRole(role);
                c.setCoachSpecialite(dto.getCoachSpecialite());
                c.setCertifications(dto.getCertifications());
                c.setTypeAbonnement(
                        dto.getTypeAbonnement() != null ? dto.getTypeAbonnement() : "MOIS_1"
                );
                coachRepository.save(c);
                token = jwtUtil.generateToken(c.getEmail(), c.getRole());
                response.put("typeAbonnement", c.getTypeAbonnement());
                break;
            }
            case "BLOOMER": {
                Bloomer b = new Bloomer();
                b.setNom(dto.getNom());
                b.setPrenom(dto.getPrenom());
                b.setEmail(dto.getEmail());
                b.setPwd(passwordEncoder.encode(dto.getPwd()));
                b.setActive(true);
                b.setRole(role);
                b.setAge(dto.getAge());
                b.setHeight(dto.getHeight());
                b.setWeight(dto.getWeight());
                b.setGoal(dto.getGoal());
                b.setLifestyleLevel(dto.getLifestyleLevel());
                b.setTypeAbonnement(
                        dto.getTypeAbonnement() != null ? dto.getTypeAbonnement() : "MOIS_1"
                );
                bloomerRepository.save(b);
                token = jwtUtil.generateToken(b.getEmail(), b.getRole());
                response.put("typeAbonnement", b.getTypeAbonnement());
                break;
            }
            default:
                throw new RuntimeException("Role invalide : " + role);
        }

        response.put("token", token);
        response.put("email", dto.getEmail());
        response.put("role", role);
        response.put("message", "Inscription réussie !");
        return response;
    }

    public Map<String, String> login(LoginDTO dto) {
        // ✅ Chercher dans toutes les tables selon le rôle
        user u = findUserByEmail(dto.getEmail());

        if (u == null) {
            throw new RuntimeException("Email introuvable !");
        }
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
        response.put("typeAbonnement", u.getTypeAbonnement() != null ? u.getTypeAbonnement() : "");
        response.put("message", "Connexion réussie !");
        return response;
    }

    // ✅ Méthode helper — cherche dans toutes les tables
    private user findUserByEmail(String email) {
        // 1. Chercher dans users (table principale)
        Optional<user> fromUsers = userRepository.findByEmail(email);
        if (fromUsers.isPresent()) return fromUsers.get();

        // 2. Chercher dans nutritionists
        Optional<Nutritionist> fromNutritionist = nutritionistRepository.findByEmail(email);
        if (fromNutritionist.isPresent()) return fromNutritionist.get();

        // 3. Chercher dans coaches
        Optional<Coach> fromCoach = coachRepository.findByEmail(email);
        if (fromCoach.isPresent()) return fromCoach.get();

        // 4. Chercher dans bloomers
        Optional<Bloomer> fromBloomer = bloomerRepository.findByEmail(email);
        if (fromBloomer.isPresent()) return fromBloomer.get();

        return null;
    }
}