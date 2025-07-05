package tn.esprit.spring.missionentreprise.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.Phase;
import tn.esprit.spring.missionentreprise.Services.PhaseService;

import java.util.List;

@RestController
@RequestMapping("/api/phases")
@CrossOrigin(origins = "*")
public class PhaseController {

    @Autowired
    private PhaseService phaseService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ENSEIGNANT') or hasRole('ROLE_COORDINATEUR')")
    public Phase ajouterPhase(@RequestBody Phase phase) {
        return phaseService.ajouterPhase(phase);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ENSEIGNANT') or hasRole('ROLE_COORDINATEUR')")
    public List<Phase> getAllPhases() {
        return phaseService.getAllPhases();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ENSEIGNANT') or hasRole('ROLE_COORDINATEUR')")
    public Phase getPhaseById(@PathVariable Long id) {
        return phaseService.getPhaseById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ENSEIGNANT') or hasRole('ROLE_COORDINATEUR')")
    public Phase modifierPhase(@PathVariable Long id, @RequestBody Phase phase) {
        return phaseService.modifierPhase(id, phase);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COORDINATEUR')")
    public void supprimerPhase(@PathVariable Long id) {
        phaseService.supprimerPhase(id);
    }
}