package tn.esprit.spring.missionentreprise.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Module;
import tn.esprit.spring.missionentreprise.Repositories.ModuleRepository;

import java.util.List;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    public Module ajouterModule(Module module) {
        return moduleRepository.save(module);
    }

    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    public Module getModuleById(Long id) {
        return moduleRepository.findById(id).orElse(null);
    }

    public Module modifierModule(Long id, Module newModule) {
        Module module = moduleRepository.findById(id).orElse(null);
        if (module != null) {
            module.setNomModule(newModule.getNomModule());
            module.setNiveau(newModule.getNiveau());
            return moduleRepository.save(module);
        }
        return null;
    }

    public void supprimerModule(Long id) {
        moduleRepository.deleteById(id);
    }
}
