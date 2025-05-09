//Espace collaboratif

package tn.esprit.spring.missionentreprise.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Tache;
import tn.esprit.spring.missionentreprise.Repositories.TacheRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TacheService implements IServiceGenerique<Tache>{

    private final TacheRepository tacheRepository;

    @Override
    public Tache add(Tache tache) {
        return tacheRepository.save(tache);
    }

    @Override
    public List<Tache> addAll(List<Tache> taches) {
        return tacheRepository.saveAll(taches);
    }

    @Override
    public List<Tache> getAll() {
        return tacheRepository.findAll();
    }

    @Override
    public Tache edit(Tache tache) {
        return tacheRepository.save(tache);
    }

    @Override
    public List<Tache> editAll(List<Tache> taches) {
        return tacheRepository.saveAll(taches);
    }

    @Override
    public void delete(Tache tache) {
        tacheRepository.delete(tache);

    }

    @Override
    public void deleteAll() {
        tacheRepository.deleteAll();

    }

    @Override
    public void deleteById(Long id) {
        tacheRepository.deleteById(id);
    }

    @Override
    public Tache getById(Long id) {
        return tacheRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return tacheRepository.existsById(id);
    }

    @Override
    public Long count() {
        return tacheRepository.count();
    }
}
