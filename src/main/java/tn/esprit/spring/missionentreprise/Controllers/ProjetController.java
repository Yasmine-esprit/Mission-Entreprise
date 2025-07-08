//Espace collaboratif
package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.Projet;
import tn.esprit.spring.missionentreprise.Services.ProjetService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/projets")
@AllArgsConstructor
public class ProjetController {
    private final ProjetService projetService;

    @PostMapping("/AddProjet")
    public ResponseEntity<?> add(@RequestBody Projet projet) {
        try {
            Projet savedProjet = projetService.add(projet);
            return ResponseEntity.ok(savedProjet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création : " + e.getMessage());
        }
    }
    @GetMapping("/Projets")
    public ResponseEntity<?> getAll() {
        try {
            List<Projet> projets = projetService.getAll();

            if (projets.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Aucun projet n'est trouvé.");
            }

            return ResponseEntity.ok(projets);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Une erreur est survenue : " + e.getMessage());
        }
    }

    @GetMapping("/groupes/{id}")
    public ResponseEntity<?> getProjetById(@PathVariable Long id) {
        try {
            Projet projet = projetService.getById(id);

            if (projet != null) {
                return ResponseEntity.ok(projet);
            } else {
                return ResponseEntity.ok("Aucun projet trouvé avec l'ID : " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @PutMapping("/Editprojet/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody Projet updatedProjet) {
        try {
            Projet existing = projetService.getById(id);

            if (existing != null) {
                updatedProjet.setIdProjet(id);
                Projet projet = projetService.edit(updatedProjet);
                return ResponseEntity.ok(projet);
            } else {
                return ResponseEntity.ok("Aucun projet trouvé à mettre à jour avec l'ID : " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }


    @DeleteMapping("/Deleteprojet/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            Projet existing = projetService.getById(id);

            if (existing != null) {
                projetService.deleteById(id);
                return ResponseEntity.ok("Projet supprimé avec succès.");
            } else {
                return ResponseEntity.ok("Aucun projet trouvé à supprimer avec l'ID : " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la suppression : " + e.getMessage());
        }
    }


    @DeleteMapping("/Deleteprojets")
    public ResponseEntity<?> deleteAll() {
        try {
            List<Projet> projets = projetService.getAll();

            if (projets.isEmpty()) {
                return ResponseEntity.ok("Aucun projet à supprimer.");
            }

            projetService.deleteAll();
            return ResponseEntity.ok("Tous les projets ont été supprimés.");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la suppression : " + e.getMessage());
        }
    }
}
