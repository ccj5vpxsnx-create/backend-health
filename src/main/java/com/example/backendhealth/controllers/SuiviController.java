package com.example.backendhealth.controllers;

import com.example.backendhealth.entities.Suivi_quotidien;
import com.example.backendhealth.services.SuiviService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/suivi")
public class SuiviController {

    private final SuiviService service;

    public SuiviController(SuiviService service) {
        this.service = service;
    }



    @GetMapping("/{id}")
    public Suivi_quotidien getById(@PathVariable Integer id) {
        return service.getSuivi(id);
    }

    @PostMapping
    public Suivi_quotidien create() {
        return service.createSuivi(new Suivi_quotidien());
    }

    @PutMapping("/{id}/eau")
    public Suivi_quotidien incrementEau(@PathVariable Integer id) {
        return service.incrementEau(id);
    }


    @PutMapping("/{id}/exercice")
    public Suivi_quotidien incrementExercice(@PathVariable Integer id) {
        return service.incrementExercice(id);
    }


    @PutMapping("/{id}/calories")
    public Suivi_quotidien updateCalories(
            @PathVariable Integer id,
            @RequestBody Integer value
    ) {
        return service.updateCalories(id, value);
    }

    @PutMapping("/{id}/proteines")
    public Suivi_quotidien updateProteines(
            @PathVariable Integer id,
            @RequestBody Integer value
    ) {
        return service.updateProteines(id, value);
    }

    @PutMapping("/{id}/sommeil")
    public Suivi_quotidien updateSommeil(
            @PathVariable Integer id,
            @RequestBody Integer value
    ) {
        return service.updateSommeil(id, value);
    }
}