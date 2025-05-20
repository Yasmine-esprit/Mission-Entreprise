package tn.esprit.spring.missionentreprise.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Theme;
import tn.esprit.spring.missionentreprise.Repositories.ThemeRepository;

import java.util.List;

@Service
public class ThemeService {

    @Autowired
    private ThemeRepository themeRepository;

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
}
