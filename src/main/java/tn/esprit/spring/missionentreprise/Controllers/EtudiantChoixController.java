package tn.esprit.spring.missionentreprise.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Services.EtudiantChoixService;

import java.util.Map;

@RestController
@RequestMapping("/api/etudiants")
@CrossOrigin(origins = "*")
public class EtudiantChoixController {

    @Autowired
    EtudiantChoixService etudiantChoixService;

    @PostMapping("/choix")
    @PreAuthorize("hasRole('ETUDIANT')")
    public Map<String, Object> effectuerChoix(
            @RequestBody Map<String, Long> choix,
            @RequestParam Long etudiantId) {
        return etudiantChoixService.effectuerChoixEtudiant(
                etudiantId,
                choix.get("classeId"),
                choix.get("groupeId"),
                choix.get("themeId")
        );
    }

    @GetMapping("/{etudiantId}/choix-effectue")
    public boolean verifierChoixEffectue(@PathVariable Long etudiantId) {
        return etudiantChoixService.verifierChoixEffectue(etudiantId);
    }

    @GetMapping("/{etudiantId}/choix")
    public Map<String, Object> getChoixEtudiant(@PathVariable Long etudiantId) {
        return etudiantChoixService.getChoixEtudiant(etudiantId);
    }
}
