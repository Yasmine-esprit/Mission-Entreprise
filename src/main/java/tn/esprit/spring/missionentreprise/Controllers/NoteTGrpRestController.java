package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.NoteTGrp;
import tn.esprit.spring.missionentreprise.Services.NoteTGrpService;

import java.util.List;

@RestController
@RequestMapping("/api/notes-grp")
@AllArgsConstructor
public class NoteTGrpRestController {

    private final NoteTGrpService noteService;

    @PostMapping
    public ResponseEntity<?> addNote(@RequestBody NoteTGrp note) {
        try {
            NoteTGrp savedNote = noteService.add(note);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Group note created successfully with ID: " + savedNote.getNoteGrpId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error creating group note: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNote(@PathVariable Long id) {
        NoteTGrp note = noteService.getById(id);
        if (note.getNoteGrpId() == 0L) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Group note not found with ID: " + id);
        }
        return ResponseEntity.ok(note);
    }

    @GetMapping
    public ResponseEntity<List<NoteTGrp>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody NoteTGrp note) {
        try {
            note.setNoteGrpId(id);
            NoteTGrp updated = noteService.edit(note);
            return ResponseEntity.ok("Group note updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error updating group note: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long id) {
        try {
            noteService.deleteById(id);
            return ResponseEntity.ok("Group note deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error deleting group note: " + e.getMessage());
        }
    }
}