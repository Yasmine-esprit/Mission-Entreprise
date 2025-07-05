package tn.esprit.spring.missionentreprise.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public Theme ajouterTheme(@RequestBody Theme theme) {
        return themeService.ajouterTheme(theme);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public List<Theme> getAllThemes() {
        return themeService.getAllThemes();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public Theme getThemeById(@PathVariable Long id) {
        return themeService.getThemeById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public Theme modifierTheme(@PathVariable Long id, @RequestBody Theme theme) {
        return themeService.modifierTheme(id, theme);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','COORDINATEUR')")
    public void supprimerTheme(@PathVariable Long id) {
        themeService.supprimerTheme(id);
    }

    @GetMapping("/classe/{classeId}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public List<Theme> getThemesByClasse(@PathVariable Long classeId) {
        return themeService.getThemesByClasse(classeId);
    }

    @GetMapping("/groupe/{groupeId}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public List<Theme> getThemesByGroupe(@PathVariable Long groupeId) {
        return themeService.getThemesByGroupe(groupeId);
    }

    @PostMapping("/{themeId}/associer-classe/{classeId}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public Theme associerThemeAClasse(@PathVariable Long themeId, @PathVariable Long classeId) {
        return themeService.associerThemeAClasse(themeId, classeId);
    }

    @PostMapping("/{themeId}/associer-groupe/{groupeId}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public Theme associerThemeAGroupe(@PathVariable Long themeId, @PathVariable Long groupeId) {
        return themeService.associerThemeAGroupe(themeId, groupeId);
    }
}
