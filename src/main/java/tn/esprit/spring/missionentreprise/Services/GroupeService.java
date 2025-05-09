//Espace collaboratif
package tn.esprit.spring.missionentreprise.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Groupe;
import tn.esprit.spring.missionentreprise.Repositories.GroupeRepository;
import java.util.List;

@Service
@AllArgsConstructor
public class GroupeService implements IServiceGenerique<Groupe> {


    private GroupeRepository groupeRepository;

    @Override
    public Groupe add(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    @Override
    public List<Groupe> addAll(List<Groupe> groupes) {
        return groupeRepository.saveAll(groupes);
    }

    @Override
    public List<Groupe> getAll() {
        return groupeRepository.findAll();
    }

    @Override
    public Groupe edit(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    @Override
    public List<Groupe> editAll(List<Groupe> groupes) {
        return groupeRepository.saveAll(groupes);
    }

    @Override
    public void delete(Groupe groupe) {
        groupeRepository.delete(groupe);
    }

    @Override
    public void deleteAll() {
        groupeRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        groupeRepository.deleteById(id);
    }

    @Override
    public Groupe getById(Long id) {
        return groupeRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return groupeRepository.existsById(id);
    }

    @Override
    public Long count() {
        return groupeRepository.count();
    }
}
