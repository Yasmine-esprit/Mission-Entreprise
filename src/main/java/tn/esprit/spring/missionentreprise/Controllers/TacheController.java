//Espace collaboratif

package tn.esprit.spring.missionentreprise.Controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.missionentreprise.Entities.*;
import tn.esprit.spring.missionentreprise.Repositories.TacheRepository;
import tn.esprit.spring.missionentreprise.Services.TacheService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/taches")
@AllArgsConstructor
@Slf4j
public class TacheController {
    private final TacheService tacheService;
    private final TacheRepository tacheRepository;

    @PostMapping("/create")
    public ResponseEntity<Tache> createTache(@RequestBody Tache tache) {
        log.info("creating a new task"+tache.getTitreTache());
        try {
            log.info("Received task: {}", tache); // Add detailed logging
            Tache created = tacheService.add(tache);
            log.info("Created task: {}", created);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            log.error("Error creating task", e); // Log the actual exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
<<<<<<< HEAD
    
    @GetMapping("/Projets")
    public ResponseEntity<?> getAll() {
        try {
            List<Tache> taches = tacheService.getAll();
=======
>>>>>>> 800784042b3a6f6955d33992fcb8e5a432132e7f

    @GetMapping("/all")
    public ResponseEntity<List<Tache>> getAllTaches() {
        return ResponseEntity.ok(tacheService.getAll());
    }
    @PostMapping("/with-id")
    public ResponseEntity<Tache> createTacheWithId(@RequestBody Tache tache) {
        try {
            if (tache.getIdTache() != null && tacheService.tacheExists(tache.getIdTache())) {
                return ResponseEntity.badRequest().body(null);
            }

            Tache createdTache = tacheService.createTacheWithId(tache);
            return ResponseEntity.ok(createdTache);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
    
    // ✅ Ajout endpoint /all pour compatibilité frontend
    @GetMapping("/all")
    public ResponseEntity<?> getAllTaches() {
        try {
            List<Tache> taches = tacheService.getAll();

            if (taches.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Aucune tâche n'est trouvée.");
            }

            return ResponseEntity.ok(taches);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Une erreur est survenue : " + e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Tache> getTacheById(@PathVariable Long id) {
        try {
            Tache tache = tacheService.getById(id);
            if (tache != null) {
                return ResponseEntity.ok(tache);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Tache> updateTache(@PathVariable Long id, @RequestBody Tache tache) {
        try {
            if (!tacheService.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            if (!id.equals(tache.getIdTache())) {
                return ResponseEntity.badRequest().body(null);
            }
            return ResponseEntity.ok(tacheService.edit(tache));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @PatchMapping("/partialUpdate/{id}")
    public ResponseEntity<Tache> partialUpdateTache(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        try {
            Tache updatedTache = tacheService.partialUpdate(id, updates);
            return ResponseEntity.ok(updatedTache);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTache(@PathVariable Long id) {
        try {
            if (!tacheService.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            tacheService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/attachments/add/{tacheId}")
    public ResponseEntity<Map<String, Object>> addPieceJointe(
            @PathVariable Long tacheId,
            @RequestBody PieceJointe pieceJointe) {
        try {
            Tache updatedTache = tacheService.addPieceJointe(tacheId, pieceJointe);

            // Create a simplified response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("tacheId", updatedTache.getIdTache());
            response.put("pieceJointeId", pieceJointe.getIdPieceJointe());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @PostMapping(value = "/files/upload/{tacheId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(
            @PathVariable Long tacheId,
            @RequestParam("file") MultipartFile file,
            @RequestHeader("Authorization") String authHeader) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "success", false,
                        "message", "File is empty"
                ));
            }

            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID() + fileExtension;
            Path filePath = uploadDir.resolve(uniqueFileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            PieceJointe pieceJointe = new PieceJointe();
            pieceJointe.setNom(originalFilename);
            pieceJointe.setUrl("/uploads/" + uniqueFileName); // Web-accessible path
            pieceJointe.setType(TypePieceJointe.FICHIER);
            Tache updatedTache = tacheService.addPieceJointe(tacheId, pieceJointe);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "File uploaded successfully",
                    "filePath", "/uploads/" + uniqueFileName,
                    "pieceJointeId", pieceJointe.getIdPieceJointe()
            ));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "Failed to save file: " + e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "Error processing file: " + e.getMessage()
            ));
        }
    }
    @PostMapping("/dates/update/{tacheId}")
    public ResponseEntity<Tache> updateTacheDates(
            @PathVariable Long tacheId,
            @RequestBody Map<String, String> datePayload) {
        try {
            System.out.println("=== DEBUGGING UPDATE DATES ===");
            System.out.println("TacheId: " + tacheId);
            System.out.println("Payload reçu: " + datePayload);

            LocalDate dateDebut = datePayload.get("dateDebut") != null ?
                    LocalDate.parse(datePayload.get("dateDebut")) : null;
            LocalDate dateFin = datePayload.get("dateFin") != null ?
                    LocalDate.parse(datePayload.get("dateFin")) : null;

            System.out.println("DateDebut parsée: " + dateDebut);
            System.out.println("DateFin parsée: " + dateFin);

            // Vérifier l'état avant mise à jour
            Optional<Tache> tacheAvant = tacheRepository.findById(tacheId);
            System.out.println("Tâche AVANT service: " + tacheAvant.orElse(null));

            Tache updatedTache = tacheService.updateTacheDates(tacheId, dateDebut, dateFin);

            System.out.println("Tâche retournée par service: " + updatedTache);

            // Vérifier l'état après mise à jour en base
            Optional<Tache> tacheApres = tacheRepository.findById(tacheId);
            System.out.println("Tâche APRÈS service (relue depuis DB): " + tacheApres.orElse(null));

            return ResponseEntity.ok(updatedTache);

        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/remove/{tacheId}/{pieceJointeId}")
    public ResponseEntity<?> removePieceJointe(
            @PathVariable Long tacheId,
            @PathVariable Long pieceJointeId) {
        try {
            tacheService.removePieceJointe(tacheId, pieceJointeId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}