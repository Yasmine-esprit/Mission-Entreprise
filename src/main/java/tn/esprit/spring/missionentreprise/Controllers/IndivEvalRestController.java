package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.IndivEval;
import tn.esprit.spring.missionentreprise.Services.IndivEvalService;

import java.util.List;

@RestController
@RequestMapping("/api/indiv-evals")
@AllArgsConstructor
public class IndivEvalRestController {

    private final IndivEvalService indivEvalService;

    @PostMapping
    public ResponseEntity<?> createIndivEval(@RequestBody IndivEval indivEval) {
        try {
            IndivEval saved = indivEvalService.add(indivEval);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIndivEvalById(@PathVariable Long id) {
        IndivEval indivEval = indivEvalService.getById(id);
        if (indivEval.getIndivEvalId() == 0L) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Individual evaluation not found with ID: " + id);
        }
        return ResponseEntity.ok(indivEval);
    }

    @GetMapping
    public ResponseEntity<List<IndivEval>> getAllIndivEvals() {
        return ResponseEntity.ok(indivEvalService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateIndivEval(@PathVariable Long id, @RequestBody IndivEval indivEval) {
        try {
            indivEval.setIndivEvalId(id);
            IndivEval updated = indivEvalService.edit(indivEval);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIndivEval(@PathVariable Long id) {
        indivEvalService.deleteById(id);
        return ResponseEntity.ok().build();
    }



}