package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.Critere;
import tn.esprit.spring.missionentreprise.Services.CritereService;

import java.util.List;

@RestController
@RequestMapping("/api/criteres")
@AllArgsConstructor
public class CritereRestController {

    CritereService critereService;

    @PostMapping
    public ResponseEntity<Critere> addCritere(@RequestBody Critere critere) {
        try {
            Critere savedCritere = critereService.add(critere);
            return new ResponseEntity<>(savedCritere, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Critere>> addAllCriteres(@RequestBody List<Critere> criteres) {
        List<Critere> savedCriteres = critereService.addAll(criteres);
        return new ResponseEntity<>(savedCriteres, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Critere> getCritereById(@PathVariable Long id) {
        Critere critere = critereService.getById(id);
        return critere.getIdCritere() == 0L ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(critere, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Critere>> getAllCriteres() {
        List<Critere> criteres = critereService.getAll();
        return new ResponseEntity<>(criteres, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Critere> updateCritere(@PathVariable Long id, @RequestBody Critere critere) {
        try {
            critere.setIdCritere(id);
            Critere updatedCritere = critereService.edit(critere);
            return new ResponseEntity<>(updatedCritere, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/batch")
    public ResponseEntity<List<Critere>> updateAllCriteres(@RequestBody List<Critere> criteres) {
        List<Critere> updatedCriteres = critereService.editAll(criteres);
        return new ResponseEntity<>(updatedCriteres, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCritere(@PathVariable Long id) {
        critereService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCritere(@RequestBody Critere critere) {
        critereService.delete(critere);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllCriteres() {
        critereService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> critereExists(@PathVariable Long id) {
        return new ResponseEntity<>(critereService.existsById(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countCriteres() {
        return new ResponseEntity<>(critereService.count(), HttpStatus.OK);
    }
}