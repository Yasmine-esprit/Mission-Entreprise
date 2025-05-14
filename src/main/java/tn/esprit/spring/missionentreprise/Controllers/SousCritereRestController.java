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
    public ResponseEntity<?> addSousCritere(@RequestBody SousCritere sousCritere) {
        try {
            SousCritere saved = sousCritereService.add(sousCritere);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Sub-criteria created successfully with ID: " + saved.getIdSousCritere());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error creating sub-criteria: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSousCritere(@PathVariable Long id) {
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
            return ResponseEntity.ok("Sub-criteria updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error updating sub-criteria: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSousCritere(@PathVariable Long id) {
        try {
            sousCritereService.deleteById(id);
            return ResponseEntity.ok("Sub-criteria deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error deleting sub-criteria: " + e.getMessage());
        }
    }
}