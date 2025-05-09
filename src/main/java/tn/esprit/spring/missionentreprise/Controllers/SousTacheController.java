//Espace collaboratif

package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.SousTache;
import tn.esprit.spring.missionentreprise.Services.SousTacheService;

import java.util.List;

@RestController
@RequestMapping("/api/soustaches")
@AllArgsConstructor
public class SousTacheController {

    private final SousTacheService soustacheService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SousTache sousTache) {
        try {
            SousTache savedSousTache = soustacheService.add(sousTache);
            return ResponseEntity.ok(savedSousTache);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création : " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<SousTache> sousTaches = soustacheService.getAll();

            if (sousTaches.isEmpty()) {
                return ResponseEntity.ok("Aucune sous-tâche trouvée.");
            }

            return ResponseEntity.ok(sousTaches);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur : " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSousTacheById(@PathVariable Long id) {
        try {
            SousTache sousTache = soustacheService.getById(id);

            if (sousTache != null) {
                return ResponseEntity.ok(sousTache);
            } else {
                return ResponseEntity.ok("Aucune sous-tâche trouvée avec l'ID : " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody SousTache updatedSousTache) {
        try {
            SousTache existing = soustacheService.getById(id);

            if (existing != null) {
                updatedSousTache.setIdSousTache(id);
                SousTache updated = soustacheService.edit(updatedSousTache);
                return ResponseEntity.ok(updated);
            } else {
                return ResponseEntity.ok("Aucune sous-tâche trouvée à mettre à jour avec l'ID : " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            SousTache existing = soustacheService.getById(id);

            if (existing != null) {
                soustacheService.deleteById(id);
                return ResponseEntity.ok("Sous-tâche supprimée avec succès.");
            } else {
                return ResponseEntity.ok("Aucune sous-tâche trouvée à supprimer avec l'ID : " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<?> deleteAll() {
        try {
            List<SousTache> sousTaches = soustacheService.getAll();

            if (sousTaches.isEmpty()) {
                return ResponseEntity.ok("Aucune sous-tâche à supprimer.");
            }

            soustacheService.deleteAll();
            return ResponseEntity.ok("Toutes les sous-tâches ont été supprimées.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la suppression : " + e.getMessage());
        }
    }
}
