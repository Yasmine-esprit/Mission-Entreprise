package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.SousCritere;
import tn.esprit.spring.missionentreprise.Services.SousCritereService;

import java.util.List;

@RestController
@RequestMapping("/api/sous-criteres")
@AllArgsConstructor
public class SousCritereRestController {

    private final SousCritereService sousCritereService;

    @PostMapping
    public ResponseEntity<?> createSousCritere(@RequestBody SousCritere sousCritere) {
        try {
            SousCritere saved = sousCritereService.add(sousCritere);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSousCritereById(@PathVariable Long id) {
        SousCritere sousCritere = sousCritereService.getById(id);
        if (sousCritere.getIdSousCritere() == 0L) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sub-criteria not found with ID: " + id);
        }
        return ResponseEntity.ok(sousCritere);
    }

    @GetMapping
    public ResponseEntity<List<SousCritere>> getAllSousCriteres() {
        return ResponseEntity.ok(sousCritereService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSousCritere(@PathVariable Long id, @RequestBody SousCritere sousCritere) {
        try {
            sousCritere.setIdSousCritere(id);
            SousCritere updated = sousCritereService.edit(sousCritere);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSousCritere(@PathVariable Long id) {
        sousCritereService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Additional endpoints
    @GetMapping("/search")
    public ResponseEntity<List<SousCritere>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(sousCritereService.findByNameContaining(name));
    }

    @GetMapping("/by-main-criteria/{mainCriteriaId}")
    public ResponseEntity<List<SousCritere>> getByMainCriteria(@PathVariable Long mainCriteriaId) {
        return ResponseEntity.ok(sousCritereService.findByMainCriteria(mainCriteriaId));
    }

}