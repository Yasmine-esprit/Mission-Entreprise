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
    private InstitutionService institutionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public Institution ajouterInstitution(@RequestBody Institution institution) {
        return institutionService.ajouterInstitution(institution);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public List<Institution> getAllInstitutions() {
        return institutionService.getAllInstitutions();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public Institution getInstitutionById(@PathVariable Long id) {
        return institutionService.getInstitutionById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public Institution modifierInstitution(@PathVariable Long id, @RequestBody Institution institution) {
        return institutionService.modifierInstitution(id, institution);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','COORDINATEUR')")
    public void supprimerInstitution(@PathVariable Long id) {
        institutionService.supprimerInstitution(id);
    }

    @GetMapping("/{id}/departements")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public List<Departement> getDepartementsByInstitution(@PathVariable Long id) {
        return institutionService.getDepartementsByInstitution(id);
    }

    @GetMapping("/hierarchie")
    @PreAuthorize("hasAnyRole('ADMINISTRATEUR','ENSEIGNANT','COORDINATEUR')")
    public HierarchieDTO getHierarchieComplete() {
        return institutionService.getHierarchieComplete();
    }
}
