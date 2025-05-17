package tn.esprit.spring.missionentreprise.Controllers;

import tn.esprit.spring.missionentreprise.Entities.Niveau;
import tn.esprit.spring.missionentreprise.Services.NiveauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/niveaux")
@CrossOrigin(origins = "*") // autorise le front-end Angular à accéder
public class NiveauController {

    @Autowired
    private NiveauService niveauService;

    @PostMapping
    public Niveau ajouterNiveau(@RequestBody Niveau niveau) {
        return niveauService.ajouterNiveau(niveau);
    }

    @GetMapping
    public List<Niveau> getAllNiveaux() {
        return niveauService.getAllNiveaux();
    }

    @GetMapping("/{id}")
    public Niveau getNiveauById(@PathVariable Long id) {
        return niveauService.getNiveauById(id);
    }

    @PutMapping("/{id}")
    public Niveau modifierNiveau(@PathVariable Long id, @RequestBody Niveau niveau) {
        return niveauService.modifierNiveau(id, niveau);
    }

    @DeleteMapping("/{id}")
    public void supprimerNiveau(@PathVariable Long id) {
        niveauService.supprimerNiveau(id);
    }
}
