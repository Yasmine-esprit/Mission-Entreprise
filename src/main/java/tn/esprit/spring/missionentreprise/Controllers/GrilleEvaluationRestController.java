package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.GrilleEvaluation;
import tn.esprit.spring.missionentreprise.Services.GrilleEvaluationService;

import java.util.List;

@RestController
@RequestMapping("/api/grilles")
@AllArgsConstructor
public class GrilleEvaluationRestController {

    private final GrilleEvaluationService grilleService;

    @PostMapping
    public ResponseEntity<?> createGrille(@RequestBody GrilleEvaluation grille) {
        try {
            GrilleEvaluation saved = grilleService.add(grille);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Grille created successfully with ID: " + saved.getIdEvaluation());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error creating grille: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGrille(@PathVariable Long id) {
        GrilleEvaluation grille = grilleService.getById(id);
        if (grille.getIdEvaluation() == 0L) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Grille not found with ID: " + id);
        }
        return ResponseEntity.ok(grille);
    }
}