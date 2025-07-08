package tn.esprit.spring.missionentreprise.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Classe ajouterClasse(@RequestBody Classe classe) {
        return classeService.ajouterClasse(classe);
    }

    @GetMapping
    public List<Classe> getAllClasses() {
        return classeService.getAllClasses();
    }

    @GetMapping("/{id}")
    public Classe getClasseById(@PathVariable Long id) {
        return classeService.getClasseById(id);
    }

    @PutMapping("/{id}")
    public Classe modifierClasse(@PathVariable Long id, @RequestBody Classe classe) {
        return classeService.modifierClasse(id, classe);
    }

    @DeleteMapping("/{id}")
    public void supprimerClasse(@PathVariable Long id) {
        classeService.supprimerClasse(id);
    }
    
    @GetMapping("/departement/{departementId}")
    public List<Classe> getClassesByDepartement(@PathVariable Long departementId) {
        return classeService.getClassesByDepartement(departementId);
    }
    
    @GetMapping("/niveau/{niveauId}")
    public List<Classe> getClassesByNiveau(@PathVariable Long niveauId) {
        return classeService.getClassesByNiveau(niveauId);
    }
}
