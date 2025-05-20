package tn.esprit.spring.missionentreprise.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.Module;
import tn.esprit.spring.missionentreprise.Services.ModuleService;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
@CrossOrigin(origins = "*")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @PostMapping
    public Module ajouterModule(@RequestBody Module module) {
        return moduleService.ajouterModule(module);
    }

    @GetMapping
    public List<Module> getAllModules() {
        return moduleService.getAllModules();
    }

    @GetMapping("/{id}")
    public Module getModuleById(@PathVariable Long id) {
        return moduleService.getModuleById(id);
    }

    @PutMapping("/{id}")
    public Module modifierModule(@PathVariable Long id, @RequestBody Module module) {
        return moduleService.modifierModule(id, module);
    }

    @DeleteMapping("/{id}")
    public void supprimerModule(@PathVariable Long id) {
        moduleService.supprimerModule(id);
    }

}
