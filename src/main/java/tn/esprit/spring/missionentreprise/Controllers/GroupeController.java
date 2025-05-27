//Espace collaboratif

package tn.esprit.spring.missionentreprise.Controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.Groupe;
import tn.esprit.spring.missionentreprise.Services.GroupeService;


import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/groupes")
@AllArgsConstructor
public class GroupeController {
    private final GroupeService groupeService;

    @PostMapping("/AddGroupe")
    public ResponseEntity<?> add(@RequestBody Groupe groupe) {
        try {
            Groupe savedGroupe = groupeService.add(groupe);
            return ResponseEntity.ok(savedGroupe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création : " + e.getMessage());
        }
    }
    @GetMapping("/groupes")
    public ResponseEntity<?> getAll() {
        try {
            List<Groupe> groupes = groupeService.getAll();

            if (groupes.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Aucun groupe n'est trouvé.");
            }

            return ResponseEntity.ok(groupes);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Une erreur est survenue : " + e.getMessage());
        }
    }

    @GetMapping("/groupes/{id}")
    public ResponseEntity<?> getGroupeById(@PathVariable Long id) {
        try {
            Groupe groupe = groupeService.getById(id);

            if (groupe != null) {
                return ResponseEntity.ok(groupe);
            } else {
                return ResponseEntity.ok("Aucun groupe trouvé avec l'ID : " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @PutMapping("/Editgroupes/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody Groupe updatedGroupe) {
        try {
            Groupe existing = groupeService.getById(id);

            if (existing != null) {
                updatedGroupe.setIdGroupe(id);
                Groupe groupe = groupeService.edit(updatedGroupe);
                return ResponseEntity.ok(groupe);
            } else {
                return ResponseEntity.ok("Aucun groupe trouvé à mettre à jour avec l'ID : " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }


    @DeleteMapping("/Deletegroupes/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            Groupe existing = groupeService.getById(id);

            if (existing != null) {
                groupeService.deleteById(id);
                return ResponseEntity.ok("Groupe supprimé avec succès.");
            } else {
                return ResponseEntity.ok("Aucun groupe trouvé à supprimer avec l'ID : " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la suppression : " + e.getMessage());
        }
    }


    @DeleteMapping("/Deletegroupes")
    public ResponseEntity<?> deleteAll() {
        try {
            List<Groupe> groupes = groupeService.getAll();

            if (groupes.isEmpty()) {
                return ResponseEntity.ok("Aucun groupe à supprimer.");
            }

            groupeService.deleteAll();
            return ResponseEntity.ok("Tous les groupes ont été supprimés.");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la suppression : " + e.getMessage());
        }
    }
}
