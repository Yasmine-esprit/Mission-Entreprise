package tn.esprit.spring.missionentreprise.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.Departement;
import tn.esprit.spring.missionentreprise.Entities.Institution;
import tn.esprit.spring.missionentreprise.Entities.HierarchieDTO;
import tn.esprit.spring.missionentreprise.Services.InstitutionService;

import java.util.List;

@RestController
@RequestMapping("/api/institutions")
@CrossOrigin(origins = "*")
public class InstitutionController {

    @Autowired
    InstitutionService institutionService;

    @PostMapping
    @PreAuthorize("hasRole('ENSEIGNANT') or hasRole('COORDINATEUR')")
    public Institution ajouterInstitution(@RequestBody Institution institution) {
        return institutionService.ajouterInstitution(institution);
    }

    @GetMapping
    public List<Institution> getAllInstitutions() {
        return institutionService.getAllInstitutions();
    }

    @GetMapping("/{id}")
    public Institution getInstitutionById(@PathVariable Long id) {
        return institutionService.getInstitutionById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ENSEIGNANT') or hasRole('COORDINATEUR')")
    public Institution modifierInstitution(@PathVariable Long id, @RequestBody Institution institution) {
        return institutionService.modifierInstitution(id, institution);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ENSEIGNANT') or hasRole('COORDINATEUR')")
    public void supprimerInstitution(@PathVariable Long id) {
        institutionService.supprimerInstitution(id);
    }
    
    @GetMapping("/{id}/departements")
    public List<Departement> getDepartementsByInstitution(@PathVariable Long id) {
        return institutionService.getDepartementsByInstitution(id);
    }
    
    @GetMapping("/hierarchie")
    public HierarchieDTO getHierarchieComplete() {
        return institutionService.getHierarchieComplete();
    }
}
