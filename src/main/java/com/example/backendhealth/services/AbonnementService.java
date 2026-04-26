package com.example.backendhealth.services;

import com.example.backendhealth.dto.AbonnementDTO;
import com.example.backendhealth.entities.Abonnement;
import com.example.backendhealth.entities.Abonnement.TypeAbonnement;
import com.example.backendhealth.entities.Abonnement.StatutAbonnement;
import com.example.backendhealth.entities.user;
import com.example.backendhealth.repositories.AbonnementRepository;
import com.example.backendhealth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AbonnementService {

    private final AbonnementRepository abonnementRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    // ─── PAIEMENT + CRÉATION ABONNEMENT ───────────────────────────────────────
    @Transactional
    public AbonnementDTO.PaymentResponse traiterPaiement(
            AbonnementDTO.PaymentRequest request, String email) {

        // 1. Valider la carte
        validerCarte(request);

        // 2. Récupérer l'utilisateur
        user currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // 3. Vérifier pas d'abonnement actif déjà
        Optional<Abonnement> existant = abonnementRepository
                .findAbonnementActifByuserId(currentUser.getId(), LocalDate.now());
        if (existant.isPresent()) {
            throw new RuntimeException("Vous avez déjà un abonnement actif !");
        }

        // 4. Créer l'abonnement
        TypeAbonnement type = TypeAbonnement.valueOf(request.getTypeAbonnement());

        Abonnement abonnement = new Abonnement();
        abonnement.setType_abonnement(type);
        abonnement.setMontant(type.prix);
        abonnement.setDateDebut(LocalDate.now());
        abonnement.setDateFin(LocalDate.now().plusMonths(type.dureeEnMois));
        abonnement.setStatut(StatutAbonnement.ACTIF);
        abonnement.setStripeSessionId(UUID.randomUUID().toString());
        abonnement.setUserEntity(currentUser);

        abonnementRepository.save(abonnement);

        // 5. Email confirmation
        emailService.sendSubscriptionConfirmation(currentUser.getEmail(), type.name());

        // 6. Réponse
        AbonnementDTO.PaymentResponse response = new AbonnementDTO.PaymentResponse();
        response.setSuccess(true);
        response.setMessage("Paiement effectué avec succès !");
        response.setTypeAbonnement(type.name());
        response.setMontant(type.prix);
        response.setSessionId(abonnement.getStripeSessionId());

        return response;
    }

    // ─── VALIDATION CARTE (fake) ───────────────────────────────────────────────
    private void validerCarte(AbonnementDTO.PaymentRequest request) {
        // Numéro : 16 chiffres
        String numero = request.getNumeroCarte().replaceAll("\\s", "");
        if (numero.length() != 16 || !numero.matches("\\d+")) {
            throw new RuntimeException("Numéro de carte invalide !");
        }

        // Date : MM/YY et pas expirée
        if (!request.getDateExpiration().matches("(0[1-9]|1[0-2])/\\d{2}")) {
            throw new RuntimeException("Date d'expiration invalide ! (format MM/YY)");
        }
        String[] parts = request.getDateExpiration().split("/");
        int mois = Integer.parseInt(parts[0]);
        int annee = 2000 + Integer.parseInt(parts[1]);
        LocalDate expDate = LocalDate.of(annee, mois, 1)
                .plusMonths(1).minusDays(1);
        if (expDate.isBefore(LocalDate.now())) {
            throw new RuntimeException("Carte expirée !");
        }

        // CVV : 3 chiffres
        if (!request.getCvv().matches("\\d{3}")) {
            throw new RuntimeException("CVV invalide !");
        }

        // Nom : pas vide
        if (request.getNomCarte() == null || request.getNomCarte().trim().isEmpty()) {
            throw new RuntimeException("Nom sur la carte requis !");
        }

        // Type abonnement : pas vide
        if (request.getTypeAbonnement() == null || request.getTypeAbonnement().trim().isEmpty()) {
            throw new RuntimeException("Type d'abonnement requis !");
        }
    }

    // ─── CRÉER ABONNEMENT (ancien endpoint gardé) ─────────────────────────────
    @Transactional
    public AbonnementDTO.PaymentResponse creerAbonnement(
            AbonnementDTO.CreatePaymentRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        user currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        TypeAbonnement type = TypeAbonnement.valueOf(request.getTypeAbonnement());

        Abonnement abonnement = new Abonnement();
        abonnement.setType_abonnement(type);
        abonnement.setMontant(type.prix);
        abonnement.setDateDebut(LocalDate.now());
        abonnement.setDateFin(LocalDate.now().plusMonths(type.dureeEnMois));
        abonnement.setStatut(StatutAbonnement.ACTIF);
        abonnement.setStripeSessionId(UUID.randomUUID().toString());
        abonnement.setUserEntity(currentUser);

        abonnementRepository.save(abonnement);
        emailService.sendSubscriptionConfirmation(currentUser.getEmail(), type.name());

        AbonnementDTO.PaymentResponse response = new AbonnementDTO.PaymentResponse();
        response.setSessionId(abonnement.getStripeSessionId());
        response.setTypeAbonnement(type.name());
        response.setMontant(type.prix);
        return response;
    }

    // ─── STATUT ───────────────────────────────────────────────────────────────
    public AbonnementDTO.StatutAbonnementResponse verifierStatut(String email) {
        AbonnementDTO.StatutAbonnementResponse statut =
                new AbonnementDTO.StatutAbonnementResponse();

        user currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Optional<Abonnement> opt = abonnementRepository
                .findAbonnementActifByuserId(currentUser.getId(), LocalDate.now());

        if (opt.isPresent()) {
            Abonnement a = opt.get();
            long jours = ChronoUnit.DAYS.between(LocalDate.now(), a.getDateFin());
            statut.setHasActiveSubscription(true);
            statut.setTypeAbonnement(a.getType_abonnement().name());
            statut.setDateFin(a.getDateFin());
            statut.setJoursRestants(jours);
            statut.setMessage("Abonnement actif - " + jours + " jours restants");
        } else {
            statut.setHasActiveSubscription(false);
            statut.setMessage("Aucun abonnement actif.");
        }

        return statut;
    }

    // ─── HISTORIQUE ───────────────────────────────────────────────────────────
    public List<AbonnementDTO.AbonnementResponse> getHistorique(String email) {
        user currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return abonnementRepository
                .findAllByuserEntityId(currentUser.getId())
                .stream()
                .map(AbonnementDTO.AbonnementResponse::from)
                .collect(Collectors.toList());
    }

    // ─── ANNULER ──────────────────────────────────────────────────────────────
    @Transactional
    public void annuler(String abonnementId, String email) {
        user currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Abonnement a = abonnementRepository.findById(abonnementId)
                .orElseThrow(() -> new RuntimeException("Abonnement non trouvé"));

        if (!a.getUserEntity().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Non autorisé");
        }

        a.setStatut(StatutAbonnement.ANNULE);
        abonnementRepository.save(a);
    }

    // ─── CHECK ACCÈS ──────────────────────────────────────────────────────────
    public boolean hasActiveSubscription(String email) {
        user currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return abonnementRepository
                .findAbonnementActifByuserId(currentUser.getId(), LocalDate.now())
                .isPresent();
    }

    // ─── EXPIRATION AUTO ──────────────────────────────────────────────────────
    @Transactional
    @org.springframework.scheduling.annotation.Scheduled(cron = "0 0 0 * * ?")
    public void expirerAbonnements() {
        List<Abonnement> expires =
                abonnementRepository.findAbonnementsExpires(LocalDate.now());
        expires.forEach(a -> a.setStatut(StatutAbonnement.EXPIRE));
        abonnementRepository.saveAll(expires);
    }
}