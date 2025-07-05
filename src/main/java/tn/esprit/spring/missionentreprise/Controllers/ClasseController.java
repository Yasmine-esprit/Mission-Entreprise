package tn.esprit.spring.missionentreprise.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.Classe;
import tn.esprit.spring.missionentreprise.Services.ClasseService;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = "*")
public class ClasseController {

    @Autowired
    ClasseService classeService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ENSEIGNANT') or hasRole('ROLE_COORDINATEUR')")
    public Classe ajouterClasse(@RequestBody Classe classe) {
        return classeService.ajouterClasse(classe);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ENSEIGNANT') or hasRole('ROLE_COORDINATEUR')")
    public List<Classe> getAllClasses() {
        return classeService.getAllClasses();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ENSEIGNANT') or hasRole('ROLE_COORDINATEUR')")
    public Classe getClasseById(@PathVariable Long id) {
        return classeService.getClasseById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ENSEIGNANT') or hasRole('ROLE_COORDINATEUR')")
    public Classe modifierClasse(@PathVariable Long id, @RequestBody Classe classe) {
        return classeService.modifierClasse(id, classe);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_COORDINATEUR')")
    public void supprimerClasse(@PathVariable Long id) {
        classeService.supprimerClasse(id);
    }
    
    @GetMapping("/departement/{departementId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ENSEIGNANT') or hasRole('ROLE_COORDINATEUR')")
    public List<Classe> getClassesByDepartement(@PathVariable Long departementId) {
        return classeService.getClassesByDepartement(departementId);
    }
    
    @GetMapping("/niveau/{niveauId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ENSEIGNANT') or hasRole('ROLE_COORDINATEUR')")
    public List<Classe> getClassesByNiveau(@PathVariable Long niveauId) {
        return classeService.getClassesByNiveau(niveauId);
    }
}