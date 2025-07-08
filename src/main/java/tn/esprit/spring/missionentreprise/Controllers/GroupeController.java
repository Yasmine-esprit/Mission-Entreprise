//Espace collaboratif

package tn.esprit.spring.missionentreprise.Controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.Groupe;
import tn.esprit.spring.missionentreprise.Entities.Visibilite;
import tn.esprit.spring.missionentreprise.Services.GroupeService;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/groupe")
@AllArgsConstructor
public class GroupeController {
    private final GroupeService groupeService;

    @PostMapping("/AddGroupeWithStudentsByEmail")
    public ResponseEntity<?> addWithStudentsByEmail(@RequestBody Map<String, Object> request) {
        try {
            Map<String, Object> groupeData = (Map<String, Object>) request.get("groupe");
            Groupe groupe = new Groupe();
            groupe.setNomGroupe((String) groupeData.get("nomGroupe"));
            groupe.setVisibilite(Visibilite.valueOf((String) groupeData.get("visibilite")));

            List<String> studentEmails = (List<String>) request.get("studentEmails");
            Set<String> emails = new HashSet<>(studentEmails);

            Groupe savedGroupe = groupeService.createGroupWithStudentsByEmail(groupe, emails);
            return ResponseEntity.ok(savedGroupe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur création groupe: " + e.getMessage());
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

    @GetMapping("/groupe/{id}")
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

    @PutMapping("/Editgroupe/{id}")
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


    @DeleteMapping("/Deletegroupe/{id}")
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
    
    @GetMapping("/classe/{classeId}")
    public ResponseEntity<?> getGroupesByClasse(@PathVariable Long classeId) {
        try {
            List<Groupe> groupes = groupeService.getGroupesByClasse(classeId);
            return ResponseEntity.ok(groupes);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Erreur lors de la récupération : " + e.getMessage());
        }
    }
}
