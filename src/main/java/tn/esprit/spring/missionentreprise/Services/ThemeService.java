package tn.esprit.spring.missionentreprise.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Theme;
import tn.esprit.spring.missionentreprise.Entities.Classe;
import tn.esprit.spring.missionentreprise.Entities.Groupe;
import tn.esprit.spring.missionentreprise.Repositories.ThemeRepository;
import tn.esprit.spring.missionentreprise.Repositories.ClasseRepository;
import tn.esprit.spring.missionentreprise.Repositories.GroupeRepository;

import java.util.List;

@Service
public class ThemeService {

    @Autowired
    private ThemeRepository themeRepository;
    
    @Autowired
    private ClasseRepository classeRepository;
    
    @Autowired
    private GroupeRepository groupeRepository;

    public Theme ajouterTheme(Theme theme) {
        return themeRepository.save(theme);
    }

    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    public Theme getThemeById(Long id) {
        return themeRepository.findById(id).orElse(null);
    }

    public Theme modifierTheme(Long id, Theme updatedTheme) {
        Theme theme = themeRepository.findById(id).orElse(null);
        if (theme != null) {
            theme.setTitreTheme(updatedTheme.getTitreTheme());
            theme.setDescription(updatedTheme.getDescription());
            theme.setModule(updatedTheme.getModule());
            return themeRepository.save(theme);
        }
        return null;
    }

    public void supprimerTheme(Long id) {
        themeRepository.deleteById(id);
    }

    public List<Theme> getThemesByClasse(Long classeId) {
        return themeRepository.findByClasse_IdCLasse(classeId);
    }

    public List<Theme> getThemesByGroupe(Long groupeId) {
        return themeRepository.findByGroupe_IdGroupe(groupeId);
    }    public Theme associerThemeAClasse(Long themeId, Long classeId) {
        Theme theme = themeRepository.findById(themeId).orElse(null);
        if (theme != null) {
            Classe classe = classeRepository.findById(classeId).orElse(null);
            if (classe != null) {
                theme.setClasse(classe);
                return themeRepository.save(theme);
            }
        }
        return null;
    }
    
    public Theme associerThemeAGroupe(Long themeId, Long groupeId) {
        Theme theme = themeRepository.findById(themeId).orElse(null);
        if (theme != null) {
            Groupe groupe = groupeRepository.findById(groupeId).orElse(null);
            if (groupe != null) {
                theme.setGroupe(groupe);
                return themeRepository.save(theme);
            }
        }
        return null;
    }
}
