package tn.esprit.spring.missionentreprise.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Departement;
import tn.esprit.spring.missionentreprise.Repositories.DepartementRepository;

import java.util.List;

@Service
public class DepartementService {

    @Autowired
    DepartementRepository departementRepository;

    public Departement ajouterDepartement(Departement departement) {
        return departementRepository.save(departement);
    }

    public List<Departement> getAllDepartements() {
        return departementRepository.findAll();
    }

    public Departement getDepartementById(Long id) {
        return departementRepository.findById(id).orElse(null);
    }

    public Departement modifierDepartement(Long id, Departement departement) {
        if (departementRepository.existsById(id)) {
            departement.setIdDepartement(id);
            return departementRepository.save(departement);
        }
        return null;
    }

    public void supprimerDepartement(Long id) {
        departementRepository.deleteById(id);
    }
    
    public List<Departement> getDepartementsByInstitution(Long institutionId) {
        return departementRepository.findByInstitution_IdInstitution(institutionId);
    }
}
