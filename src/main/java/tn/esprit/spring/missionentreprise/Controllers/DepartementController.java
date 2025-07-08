package tn.esprit.spring.missionentreprise.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.Departement;
import tn.esprit.spring.missionentreprise.Services.DepartementService;

import java.util.List;

@RestController
@RequestMapping("/api/departements")
@CrossOrigin(origins = "*")
public class DepartementController {

    @Autowired
    DepartementService departementService;

    @PostMapping
    @PreAuthorize("hasRole('ENSEIGNANT') or hasRole('COORDINATEUR')")
    public Departement ajouterDepartement(@RequestBody Departement departement) {
        return departementService.ajouterDepartement(departement);
    }

    @GetMapping
    public List<Departement> getAllDepartements() {
        return departementService.getAllDepartements();
    }

    @GetMapping("/{id}")
    public Departement getDepartementById(@PathVariable Long id) {
        return departementService.getDepartementById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ENSEIGNANT') or hasRole('COORDINATEUR')")
    public Departement modifierDepartement(@PathVariable Long id, @RequestBody Departement departement) {
        return departementService.modifierDepartement(id, departement);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ENSEIGNANT') or hasRole('COORDINATEUR')")
    public void supprimerDepartement(@PathVariable Long id) {
        departementService.supprimerDepartement(id);
    }
}
