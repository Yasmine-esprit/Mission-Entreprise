package tn.esprit.spring.missionentreprise.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Phase ajouterPhase(@RequestBody Phase phase) {
        return phaseService.ajouterPhase(phase);
    }

    @GetMapping
    public List<Phase> getAllPhases() {
        return phaseService.getAllPhases();
    }

    @GetMapping("/{id}")
    public Phase getPhaseById(@PathVariable Long id) {
        return phaseService.getPhaseById(id);
    }

    @PutMapping("/{id}")
    public Phase modifierPhase(@PathVariable Long id, @RequestBody Phase phase) {
        return phaseService.modifierPhase(id, phase);
    }

    @DeleteMapping("/{id}")
    public void supprimerPhase(@PathVariable Long id) {
        phaseService.supprimerPhase(id);
    }
}
