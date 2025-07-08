package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.GrilleEvaluation;
import tn.esprit.spring.missionentreprise.Entities.TypeGrilleEval;
import tn.esprit.spring.missionentreprise.Services.GrilleEvaluationService;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
@AllArgsConstructor
public class GrilleEvaluationRestController {

    GrilleEvaluationService grilleEvaluationService;

    @PostMapping
    public ResponseEntity<GrilleEvaluation> createGrille(@RequestBody GrilleEvaluation grille) {
        try {
            GrilleEvaluation savedGrille = grilleEvaluationService.add(grille);
            return new ResponseEntity<>(savedGrille, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<List<GrilleEvaluation>> createAllGrilles(@RequestBody List<GrilleEvaluation> grilles) {
        try {
            List<GrilleEvaluation> savedGrilles = grilleEvaluationService.addAll(grilles);
            return new ResponseEntity<>(savedGrilles, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrilleEvaluation> getGrilleById(@PathVariable Long id) {
        GrilleEvaluation grille = grilleEvaluationService.getById(id);
        return grille.getIdEvaluation() == 0L ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(grille, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GrilleEvaluation>> getAllGrilles() {
        List<GrilleEvaluation> grilles = grilleEvaluationService.getAll();
        return new ResponseEntity<>(grilles, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrilleEvaluation> updateGrille(@PathVariable Long id, @RequestBody GrilleEvaluation grille) {
        try {
            grille.setIdEvaluation(id);
            GrilleEvaluation updatedGrille = grilleEvaluationService.edit(grille);
            return new ResponseEntity<>(updatedGrille, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/batch")
    public ResponseEntity<List<GrilleEvaluation>> updateAllGrilles(@RequestBody List<GrilleEvaluation> grilles) {
        try {
            List<GrilleEvaluation> updatedGrilles = grilleEvaluationService.editAll(grilles);
            return new ResponseEntity<>(updatedGrilles, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrille(@PathVariable Long id) {
        grilleEvaluationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteGrille(@RequestBody GrilleEvaluation grille) {
        grilleEvaluationService.delete(grille);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllGrilles() {
        grilleEvaluationService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> grilleExists(@PathVariable Long id) {
        return new ResponseEntity<>(grilleEvaluationService.existsById(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countGrilles() {
        return new ResponseEntity<>(grilleEvaluationService.count(), HttpStatus.OK);
    }



    @GetMapping("/by-type/{type}")
    public ResponseEntity<List<GrilleEvaluation>> getByType(@PathVariable TypeGrilleEval type) {
        List<GrilleEvaluation> grilles = grilleEvaluationService.findByTypeEval(type);
        return new ResponseEntity<>(grilles, HttpStatus.OK);
    }

    @GetMapping("/{id}/criteres")
    public ResponseEntity<?> getCriteresByGrille(@PathVariable Long id) {
        GrilleEvaluation grille = grilleEvaluationService.getById(id);
        if (grille.getIdEvaluation() == 0L) {
            return new ResponseEntity<>("Grille not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(grille.getCriteres(), HttpStatus.OK);
    }
}