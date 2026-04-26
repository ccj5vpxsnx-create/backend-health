package com.example.backendhealth.controllers;

import com.example.backendhealth.entities.ObjectifPersonnel;
import com.example.backendhealth.services.ObjectifService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/objectifs")
@CrossOrigin("*")
public class ObjectifController {

    private final ObjectifService service;

    public ObjectifController(ObjectifService service) {
        this.service = service;
    }
    @PostMapping
    public ObjectifPersonnel create(@RequestBody ObjectifPersonnel objectif) {
        return service.saveObjectif(objectif);
    }

    @GetMapping("/{id}")
    public ObjectifPersonnel getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PatchMapping("/{id}/eau")
    public ObjectifPersonnel updateEau(@PathVariable Integer id,
                                       @RequestBody Integer eau) {
        return service.updateEau(id, eau);
    }


    @PatchMapping("/{id}/sommeil")
    public ObjectifPersonnel updateSommeil(@PathVariable Integer id,
                                           @RequestBody Integer sommeil) {
        return service.updateSommeil(id, sommeil);
    }


    @PatchMapping("/{id}/exercices")
    public ObjectifPersonnel updateExercices(@PathVariable Integer id,
                                             @RequestBody Integer ex) {
        return service.updateExercices(id, ex);
    }
    @PatchMapping("/{id}/calories")
    public ObjectifPersonnel updateCalories(@PathVariable Integer id,
                                            @RequestBody Integer cal) {
        return service.updateCalories(id, cal);
    }

    @PatchMapping("/{id}/proteines")
    public ObjectifPersonnel updateProteines(@PathVariable Integer id,
                                             @RequestBody Integer prot) {
        return service.updateProteines(id, prot);
    }
}