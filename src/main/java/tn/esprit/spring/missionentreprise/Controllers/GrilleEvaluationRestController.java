package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.GrilleEvaluation;
import tn.esprit.spring.missionentreprise.Services.GrilleEvaluationService;

import java.util.List;

@RestController
@RequestMapping("/api/grilles-evaluation")
@AllArgsConstructor
public class GrilleEvaluationRestController {

    private final GrilleEvaluationService grilleEvaluationService;

    @PostMapping
    public ResponseEntity<?> createGrille(@RequestBody GrilleEvaluation grille) {
        try {
            GrilleEvaluation savedGrille = grilleEvaluationService.add(grille);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Grille evaluation created successfully with ID: " + savedGrille.getIdEvaluation());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error creating grille evaluation: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGrilleById(@PathVariable Long id) {
        GrilleEvaluation grille = grilleEvaluationService.getById(id);
        if (grille.getIdEvaluation() == 0L) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Grille evaluation not found with ID: " + id);
        }
        return ResponseEntity.ok(grille);
    }

    @GetMapping
    public ResponseEntity<List<GrilleEvaluation>> getAllGrilles() {
        return ResponseEntity.ok(grilleEvaluationService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGrille(@PathVariable Long id, @RequestBody GrilleEvaluation grille) {
        try {
            grille.setIdEvaluation(id);
            GrilleEvaluation updated = grilleEvaluationService.edit(grille);
            return ResponseEntity.ok("Grille evaluation updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error updating grille evaluation: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGrille(@PathVariable Long id) {
        try {
            grilleEvaluationService.deleteById(id);
            return ResponseEntity.ok("Grille evaluation deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error deleting grille evaluation: " + e.getMessage());
        }
    }

    // Additional endpoint to get criteres by grille
    @GetMapping("/{id}/criteres")
    public ResponseEntity<?> getCriteresByGrille(@PathVariable Long id) {
        try {
            GrilleEvaluation grille = grilleEvaluationService.getById(id);
            if (grille.getIdEvaluation() == 0L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Grille evaluation not found with ID: " + id);
            }
            return ResponseEntity.ok(grille.getCriteres());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error fetching criteres: " + e.getMessage());
        }
    }
}