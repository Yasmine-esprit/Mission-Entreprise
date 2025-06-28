package tn.esprit.spring.missionentreprise.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Departement;
import tn.esprit.spring.missionentreprise.Entities.Institution;
import tn.esprit.spring.missionentreprise.Entities.HierarchieDTO;
import tn.esprit.spring.missionentreprise.Repositories.DepartementRepository;
import tn.esprit.spring.missionentreprise.Repositories.InstitutionRepository;

import java.util.List;

@Service
public class InstitutionService {

    @Autowired
    InstitutionRepository institutionRepository;
    
    @Autowired
    DepartementRepository departementRepository;

    public Institution ajouterInstitution(Institution institution) {
        return institutionRepository.save(institution);
    }

    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }

    public Institution getInstitutionById(Long id) {
        return institutionRepository.findById(id).orElse(null);
    }

    public Institution modifierInstitution(Long id, Institution institution) {
        if (institutionRepository.existsById(id)) {
            institution.setIdInstitution(id);
            return institutionRepository.save(institution);
        }
        return null;
    }

    public void supprimerInstitution(Long id) {
        institutionRepository.deleteById(id);
    }
    
    public List<Departement> getDepartementsByInstitution(Long institutionId) {
        return departementRepository.findByInstitution_IdInstitution(institutionId);
    }
    
    public HierarchieDTO getHierarchieComplete() {
        List<Institution> institutions = institutionRepository.findAll();
        // TODO: Mapper vers DTO pour éviter les références circulaires
        return HierarchieDTO.builder()
                .institutions(null) // Implémentation simplifiée
                .build();
    }
}
