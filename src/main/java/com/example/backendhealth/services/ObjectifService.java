package com.example.backendhealth.services;
import com.example.backendhealth.entities.ObjectifPersonnel;
import com.example.backendhealth.repositories.ObjectifRepository;
import org.springframework.stereotype.Service;


@Service
public class ObjectifService {

    private final ObjectifRepository objectifRepository;


    public ObjectifService(ObjectifRepository objectifRepository) {
        this.objectifRepository = objectifRepository;
    }


    public ObjectifPersonnel saveObjectif(ObjectifPersonnel objectif) {
        return objectifRepository.save(objectif);
    }

    public ObjectifPersonnel getById(Integer id) {
        return objectifRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Objectif non trouvé"));
    }


    public ObjectifPersonnel updateEau(Integer id, Integer eau) {
        ObjectifPersonnel obj = getById(id);
        obj.setObjectif_coupes_eau(eau);
        return objectifRepository.save(obj);
    }
    public ObjectifPersonnel updateSommeil(Integer id, Integer sommeil) {
        ObjectifPersonnel obj = getById(id);
        obj.setObjectif_heures_sommeil(sommeil);
        return objectifRepository.save(obj);
    }

    public ObjectifPersonnel updateExercices(Integer id, Integer exercices) {
        ObjectifPersonnel obj = getById(id);
        obj.setObjectif_exercices_semaine(exercices);
        return objectifRepository.save(obj);
    }


    public ObjectifPersonnel updateCalories(Integer id, Integer calories) {
        ObjectifPersonnel obj = getById(id);
        obj.setObjectif_calories(calories);
        return objectifRepository.save(obj);
    }


    public ObjectifPersonnel updateProteines(Integer id, Integer proteines) {
        ObjectifPersonnel obj = getById(id);
        obj.setObjectif_proteines(proteines);
        return objectifRepository.save(obj);
    }


}
