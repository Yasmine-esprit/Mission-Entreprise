package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.NoteTIndiv;
import tn.esprit.spring.missionentreprise.Services.NoteTIndivService;

import java.util.List;

@RestController
@RequestMapping("/api/notes-indiv")
@AllArgsConstructor
public class NoteTIndivRestController {

    private final NoteTIndivService noteService;

    @PostMapping
    public ResponseEntity<?> addNote(@RequestBody NoteTIndiv note) {
        try {
            NoteTIndiv savedNote = noteService.add(note);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Individual note created successfully with ID: " + savedNote.getNoteIndivId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error creating individual note: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNote(@PathVariable Long id) {
        NoteTIndiv note = noteService.getById(id);
        if (note.getNoteIndivId() == 0L) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Individual note not found with ID: " + id);
        }
        return ResponseEntity.ok(note);
    }

    @GetMapping
    public ResponseEntity<List<NoteTIndiv>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody NoteTIndiv note) {
        try {
            note.setNoteIndivId(id);
            NoteTIndiv updated = noteService.edit(note);
            return ResponseEntity.ok("Individual note updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error updating individual note: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long id) {
        try {
            noteService.deleteById(id);
            return ResponseEntity.ok("Individual note deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error deleting individual note: " + e.getMessage());
        }
    }
}