package tn.esprit.spring.missionentreprise.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Classe;
import tn.esprit.spring.missionentreprise.Repositories.ClasseRepository;

import java.util.List;

@Service
public class ClasseService {

    @Autowired
    ClasseRepository classeRepository;

    public Classe ajouterClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }

    public Classe getClasseById(Long id) {
        return classeRepository.findById(id).orElse(null);
    }

    public Classe modifierClasse(Long id, Classe updatedClasse) {
        Classe classe = classeRepository.findById(id).orElse(null);
        if (classe != null) {
            classe.setNomClasse(updatedClasse.getNomClasse());
            classe.setNiveau(updatedClasse.getNiveau());
            return classeRepository.save(classe);
        }
        return null;
    }

    public void supprimerClasse(Long id) {
        classeRepository.deleteById(id);
    }

    public List<Classe> getClassesByDepartement(Long departementId) {
        return classeRepository.findByDepartement_IdDepartement(departementId);
    }

    public List<Classe> getClassesByNiveau(Long niveauId) {
        return classeRepository.findByNiveau_IdNiveau(niveauId);
    }
}
