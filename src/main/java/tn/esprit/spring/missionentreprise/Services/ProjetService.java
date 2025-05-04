//Espace collaboratif

package tn.esprit.spring.missionentreprise.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Projet;
import tn.esprit.spring.missionentreprise.Repositories.ProjetRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjetService implements IServiceGenerique<Projet> {
    @Autowired
    private ProjetRepository projetRepository;

    @Override
    public Projet add(Projet projet) {
        return projetRepository.save(projet);
    }

    @Override
    public List<Projet> addAll(List<Projet> projets) {
        return projetRepository.saveAll(projets);
    }

    @Override
    public List<Projet> getAll() {
        return projetRepository.findAll();
    }

    @Override
    public Projet edit(Projet projet) {
        return projetRepository.save(projet);
    }

    @Override
    public List<Projet> editAll(List<Projet> projets) {
        return projetRepository.saveAll(projets);
    }

    @Override
    public void delete(Projet projet) {
        projetRepository.delete(projet);
    }

    @Override
    public void deleteAll() {
        projetRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        projetRepository.deleteById(id);
    }

    @Override
    public Projet getById(Long id) {
        return projetRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return projetRepository.existsById(id);
    }

    @Override
    public Long count() {
        return projetRepository.count();
    }
}
