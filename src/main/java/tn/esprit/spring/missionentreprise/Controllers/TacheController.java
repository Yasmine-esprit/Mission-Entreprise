//Espace collaboratif

package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.Tache;
import tn.esprit.spring.missionentreprise.Services.TacheService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/taches")
@AllArgsConstructor
public class TacheController {
    private final TacheService tacheService;

    @PostMapping("/AddTache")
    public ResponseEntity<?> add(@RequestBody Tache tache) {
        try {
            Tache savedTache = tacheService.add(tache);
            return ResponseEntity.ok(savedTache);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création : " + e.getMessage());
        }
    }
    @GetMapping("/Projets")
    public ResponseEntity<?> getAll() {
        try {
            List<Tache> taches = tacheService.getAll();

            if (taches.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Aucun projet n'est trouvé.");
            }

            return ResponseEntity.ok(taches);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Une erreur est survenue : " + e.getMessage());
        }
    }

    @GetMapping("/taches/{id}")
    public ResponseEntity<?> getTacheById(@PathVariable Long id) {
        try {
            Tache tache = tacheService.getById(id);

            if (tache != null) {
                return ResponseEntity.ok(tache);
            } else {
                return ResponseEntity.ok("Aucun tache trouvé avec l'ID : " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @PutMapping("/Edittache/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody Tache updatedTache) {
        try {
            Tache existing = tacheService.getById(id);

            if (existing != null) {
                updatedTache.setIdTache(id);
                Tache tache = tacheService.edit(updatedTache);
                return ResponseEntity.ok(tache);
            } else {
                return ResponseEntity.ok("Aucune tache trouvée à mettre à jour avec l'ID : " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }


    @DeleteMapping("/Deletetache/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            Tache existing = tacheService.getById(id);

            if (existing != null) {
                tacheService.deleteById(id);
                return ResponseEntity.ok("Tache supprimée avec succès.");
            } else {
                return ResponseEntity.ok("Aucune tache trouvée à supprimer avec l'ID : " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la suppression : " + e.getMessage());
        }
    }


    @DeleteMapping("/Deletetaches")
    public ResponseEntity<?> deleteAll() {
        try {
            List<Tache> taches = tacheService.getAll();

            if (taches.isEmpty()) {
                return ResponseEntity.ok("Aucune tache à supprimer.");
            }

            tacheService.deleteAll();
            return ResponseEntity.ok("Toutes les taches ont été supprimées.");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la suppression : " + e.getMessage());
        }
    }
}
