package com.example.backendhealth.services;

import com.example.backendhealth.entities.Suivi_quotidien;
import com.example.backendhealth.repositories.SuiviRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuiviService {

    private final SuiviRepository repository;

    public SuiviService(SuiviRepository repository) {
        this.repository = repository;
    }

    public Suivi_quotidien getSuivi(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    public Suivi_quotidien createSuivi(Suivi_quotidien suivi) {

        suivi.setNb_coupes_bues(0);
        suivi.setNb_exercices_faites(0);
        suivi.setNb_heures_sommeil(0);
        suivi.setCalories_consommes(0);
        suivi.setProteines_consommes(0);

        if (suivi.getDate() == null) {
            suivi.setDate(new java.util.Date());
        }

        return repository.save(suivi);
    }


    public Suivi_quotidien incrementEau(Integer id) {
        Suivi_quotidien suivi = repository.findById(id).orElseThrow();

        int current = suivi.getNb_coupes_bues() == null ? 0 : suivi.getNb_coupes_bues();
        suivi.setNb_coupes_bues(current + 1);

        return repository.save(suivi);
    }

    public Suivi_quotidien incrementExercice(Integer id) {
        Suivi_quotidien suivi = repository.findById(id).orElseThrow();

        int current = suivi.getNb_exercices_faites() == null ? 0 : suivi.getNb_exercices_faites();
        suivi.setNb_exercices_faites(current + 1);

        return repository.save(suivi);
    }

    public Suivi_quotidien updateCalories(Integer id, Integer calories) {
        Suivi_quotidien suivi = repository.findById(id).orElseThrow();
        suivi.setCalories_consommes(calories);
        return repository.save(suivi);
    }

    public Suivi_quotidien updateProteines(Integer id, Integer proteines) {
        Suivi_quotidien suivi = repository.findById(id).orElseThrow();
        suivi.setProteines_consommes(proteines);
        return repository.save(suivi);
    }

    public Suivi_quotidien updateSommeil(Integer id, Integer heures) {
        Suivi_quotidien suivi = repository.findById(id).orElseThrow();
        suivi.setNb_heures_sommeil(heures);
        return repository.save(suivi);
    }
}