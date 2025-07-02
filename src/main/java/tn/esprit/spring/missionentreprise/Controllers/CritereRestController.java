package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.Critere;
import tn.esprit.spring.missionentreprise.Services.CritereService;

import java.util.List;

@RestController
@RequestMapping("/api/criteres")
@AllArgsConstructor
public class CritereRestController {


    private final CritereService critereService;

    @PostMapping
    public ResponseEntity<?> addCritere(@RequestBody Critere critere) {
        try {
            Critere savedCritere = critereService.add(critere);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Critere created successfully with ID: " + savedCritere.getIdCritere());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error creating critere: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCritere(@PathVariable Long id) {
        Critere critere = critereService.getById(id);
        if (critere.getIdCritere() == 0L) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Critere not found with ID: " + id);
        }
        return ResponseEntity.ok(critere);
    }

    @GetMapping
    public ResponseEntity<List<Critere>> getAllCriteres() {
        return ResponseEntity.ok(critereService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCritere(@PathVariable Long id, @RequestBody Critere critere) {
        try {
            critere.setIdCritere(id);
            Critere updated = critereService.edit(critere);
            return ResponseEntity.ok("Critere updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error updating critere: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCritere(@PathVariable Long id) {
        try {
            critereService.deleteById(id);
            return ResponseEntity.ok("Criterniahauyhe deleted successfully idk what im ttypping but im trring my best to write sth for ");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error deleting critere: " + e.getMessage());
        }
    }
}