package tn.esprit.spring.missionentreprise.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.Theme;
import tn.esprit.spring.missionentreprise.Services.ThemeService;

import java.util.List;

@RestController
@RequestMapping("/api/themes")
@CrossOrigin(origins = "*")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @PostMapping
    public Theme ajouterTheme(@RequestBody Theme theme) {
        return themeService.ajouterTheme(theme);
    }

    @GetMapping
    public List<Theme> getAllThemes() {
        return themeService.getAllThemes();
    }

    @GetMapping("/{id}")
    public Theme getThemeById(@PathVariable Long id) {
        return themeService.getThemeById(id);
    }

    @PutMapping("/{id}")
    public Theme modifierTheme(@PathVariable Long id, @RequestBody Theme theme) {
        return themeService.modifierTheme(id, theme);
    }

    @DeleteMapping("/{id}")
    public void supprimerTheme(@PathVariable Long id) {
        themeService.supprimerTheme(id);
    }

    @GetMapping("/classe/{classeId}")
    public List<Theme> getThemesByClasse(@PathVariable Long classeId) {
        return themeService.getThemesByClasse(classeId);
    }

    @GetMapping("/groupe/{groupeId}")
    public List<Theme> getThemesByGroupe(@PathVariable Long groupeId) {
        return themeService.getThemesByGroupe(groupeId);
    }

    @PostMapping("/{themeId}/associer-classe/{classeId}")
    public Theme associerThemeAClasse(@PathVariable Long themeId, @PathVariable Long classeId) {
        return themeService.associerThemeAClasse(themeId, classeId);
    }

    @PostMapping("/{themeId}/associer-groupe/{groupeId}")
    public Theme associerThemeAGroupe(@PathVariable Long themeId, @PathVariable Long groupeId) {
        return themeService.associerThemeAGroupe(themeId, groupeId);
    }
}
