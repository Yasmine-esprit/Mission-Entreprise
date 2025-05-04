//Espace collaboratif

package tn.esprit.spring.missionentreprise.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.SousTache;
import tn.esprit.spring.missionentreprise.Repositories.SousTacheRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SousTacheService implements IServiceGenerique<SousTache>{
    @Autowired
    private SousTacheRepository sousTacheRepository;

    @Override
    public SousTache add(SousTache sousTache) {
        return sousTacheRepository.save(sousTache);
    }

    @Override
    public List<SousTache> addAll(List<SousTache> sousTaches) {
        return sousTacheRepository.saveAll(sousTaches);
    }

    @Override
    public List<SousTache> getAll() {
        return sousTacheRepository.findAll();
    }

    @Override
    public SousTache edit(SousTache sousTache) {
        return sousTacheRepository.save(sousTache);
    }

    @Override
    public List<SousTache> editAll(List<SousTache> sousTaches) {
        return sousTacheRepository.saveAll(sousTaches);
    }

    @Override
    public void delete(SousTache sousTache) {
         sousTacheRepository.delete(sousTache);
    }

    @Override
    public void deleteAll() {
        sousTacheRepository.deleteAll();

    }

    @Override
    public void deleteById(Long id) {
        sousTacheRepository.deleteById(id);

    }

    @Override
    public SousTache getById(Long id) {
        return sousTacheRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return sousTacheRepository.existsById(id);
    }

    @Override
    public Long count() {
        return sousTacheRepository.count();
    }
}
