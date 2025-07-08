package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.GroupEval;
import tn.esprit.spring.missionentreprise.Services.GroupEvalService;

import java.util.List;

@RestController
@RequestMapping("/api/group-evals")
@AllArgsConstructor
public class GroupEvalRestController {

    private final GroupEvalService groupEvalService;

    @PostMapping
    public ResponseEntity<?> createGroupEval(@RequestBody GroupEval groupEval) {
        try {
            GroupEval saved = groupEvalService.add(groupEval);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGroupEvalById(@PathVariable Long id) {
        GroupEval groupEval = groupEvalService.getById(id);
        if (groupEval.getNoteGrpId() == 0L) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Group evaluation not found with ID: " + id);
        }
        return ResponseEntity.ok(groupEval);
    }

    @GetMapping
    public ResponseEntity<List<GroupEval>> getAllGroupEvals() {
        return ResponseEntity.ok(groupEvalService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroupEval(@PathVariable Long id, @RequestBody GroupEval groupEval) {
        try {
            groupEval.setNoteGrpId(id);
            GroupEval updated = groupEvalService.edit(groupEval);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupEval(@PathVariable Long id) {
        groupEvalService.deleteById(id);
        return ResponseEntity.ok().build();
    }




}