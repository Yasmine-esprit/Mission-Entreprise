package tn.esprit.spring.missionentreprise.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Classe;
import tn.esprit.spring.missionentreprise.Entities.Etudiant;
import tn.esprit.spring.missionentreprise.Entities.Groupe;
import tn.esprit.spring.missionentreprise.Entities.Theme;
import tn.esprit.spring.missionentreprise.Repositories.ClasseRepository;
import tn.esprit.spring.missionentreprise.Repositories.EtudiantRepository;
import tn.esprit.spring.missionentreprise.Repositories.GroupeRepository;
import tn.esprit.spring.missionentreprise.Repositories.ThemeRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class EtudiantChoixService {

    @Autowired
    EtudiantRepository etudiantRepository;
    
    @Autowired
    ClasseRepository classeRepository;
    
    @Autowired
    GroupeRepository groupeRepository;
    
    @Autowired
    ThemeRepository themeRepository;

    public Map<String, Object> effectuerChoixEtudiant(Long etudiantId, Long classeId, Long groupeId, Long themeId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
            if (etudiant == null) {
                response.put("success", false);
                response.put("message", "Étudiant non trouvé");
                return response;
            }
            
            if (etudiant.getChoixEffectue() != null && etudiant.getChoixEffectue()) {
                response.put("success", false);
                response.put("message", "Vous avez déjà effectué votre choix");
                return response;
            }
            
            Classe classe = classeRepository.findById(classeId).orElse(null);
            Groupe groupe = groupeRepository.findById(groupeId).orElse(null);
            Theme theme = themeRepository.findById(themeId).orElse(null);
            
            if (classe == null || groupe == null || theme == null) {
                response.put("success", false);
                response.put("message", "Données invalides");
                return response;
            }
            
            etudiant.setClasse(classe);
            etudiant.setGroupe(groupe);
            etudiant.setThemeChoisi(theme);
            etudiant.setChoixEffectue(true);
            
            etudiantRepository.save(etudiant);
            
            response.put("success", true);
            response.put("message", "Choix enregistré avec succès");
            return response;
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erreur lors de l'enregistrement: " + e.getMessage());
            return response;
        }
    }

    public boolean verifierChoixEffectue(Long etudiantId) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
        return etudiant != null && etudiant.getChoixEffectue() != null && etudiant.getChoixEffectue();
    }

    public Map<String, Object> getChoixEtudiant(Long etudiantId) {
        Map<String, Object> choix = new HashMap<>();
        Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
        
        if (etudiant != null) {
            choix.put("classe", etudiant.getClasse());
            choix.put("groupe", etudiant.getGroupe());
            choix.put("theme", etudiant.getThemeChoisi());
            choix.put("choixEffectue", etudiant.getChoixEffectue());
        }
        
        return choix;
    }
}
