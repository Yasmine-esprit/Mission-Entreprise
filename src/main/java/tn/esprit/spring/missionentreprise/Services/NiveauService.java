package tn.esprit.spring.missionentreprise.Services;

import tn.esprit.spring.missionentreprise.Entities.Niveau;
import tn.esprit.spring.missionentreprise.Repositories.NiveauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NiveauService {

    @Autowired
    private NiveauRepository niveauRepository;

    public Niveau ajouterNiveau(Niveau niveau) {
        return niveauRepository.save(niveau);
    }

    public List<Niveau> getAllNiveaux() {
        return niveauRepository.findAll();
    }

    public Niveau getNiveauById(Long id) {
        return niveauRepository.findById(id).orElse(null);
    }

    public Niveau modifierNiveau(Long id, Niveau newNiveau) {
        Niveau niveau = niveauRepository.findById(id).orElse(null);
        if (niveau != null) {
            niveau.setNomNiveau(newNiveau.getNomNiveau());
            return niveauRepository.save(niveau);
        }
        return null;
    }

    public void supprimerNiveau(Long id) {
        niveauRepository.deleteById(id);
    }
}
