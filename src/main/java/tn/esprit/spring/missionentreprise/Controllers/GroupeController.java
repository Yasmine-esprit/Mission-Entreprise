package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.Groupe;
import tn.esprit.spring.missionentreprise.Services.GroupeService;

import java.util.List;

@RestController
@RequestMapping("/api/groupes")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class GroupeController {

    private final GroupeService groupeService;

    // Création compatible frontend
    @PostMapping({"/add", "/AddGroupe"})
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public ResponseEntity<?> addGroupe(@RequestBody Groupe groupe) {
        try {
            Groupe savedGroupe = groupeService.add(groupe);
            return ResponseEntity.ok(savedGroupe);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Erreur lors de la création : " + e.getMessage());
        }
    }

    // Liste complète compatible frontend
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public ResponseEntity<?> getAllGroupes() {
        try {
            List<Groupe> groupes = groupeService.getAll();
            return ResponseEntity.ok(groupes);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Une erreur est survenue : " + e.getMessage());
        }
    }

    // Ancien endpoint conservé
    @GetMapping("/groupes")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public ResponseEntity<?> getAllFallback() {
        try {
            List<Groupe> groupes = groupeService.getAll();
            if (groupes.isEmpty()) {
                return ResponseEntity.ok("Aucun groupe n'est trouvé.");
            }
            return ResponseEntity.ok(groupes);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Une erreur est survenue : " + e.getMessage());
        }
    }

    @GetMapping("/groupes/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public ResponseEntity<?> getGroupeById(@PathVariable Long id) {
        try {
            Groupe groupe = groupeService.getById(id);
            if (groupe != null) {
                return ResponseEntity.ok(groupe);
            }
            return ResponseEntity.ok("Aucun groupe trouvé avec l'ID : " + id);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Erreur : " + e.getMessage());
        }
    }

    @PutMapping("/Editgroupes/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public ResponseEntity<?> editGroupe(@PathVariable Long id, @RequestBody Groupe updatedGroupe) {
        try {
            Groupe existing = groupeService.getById(id);
            if (existing != null) {
                updatedGroupe.setIdGroupe(id);
                Groupe groupe = groupeService.edit(updatedGroupe);
                return ResponseEntity.ok(groupe);
            }
            return ResponseEntity.ok("Aucun groupe trouvé à mettre à jour avec l'ID : " + id);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    @DeleteMapping("/Deletegroupes/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','COORDINATEUR')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            Groupe existing = groupeService.getById(id);
            if (existing != null) {
                groupeService.deleteById(id);
                return ResponseEntity.ok("Groupe supprimé avec succès.");
            }
            return ResponseEntity.ok("Aucun groupe trouvé à supprimer avec l'ID : " + id);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @DeleteMapping("/Deletegroupes")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','COORDINATEUR')")
    public ResponseEntity<?> deleteAllGroupes() {
        try {
            List<Groupe> groupes = groupeService.getAll();
            if (groupes.isEmpty()) {
                return ResponseEntity.ok("Aucun groupe à supprimer.");
            }
            groupeService.deleteAll();
            return ResponseEntity.ok("Tous les groupes ont été supprimés.");
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Erreur lors de la suppression : " + e.getMessage());
        }
    }
    
    @GetMapping("/classe/{classeId}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
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
