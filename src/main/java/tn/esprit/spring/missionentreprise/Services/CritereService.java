package tn.esprit.spring.missionentreprise.Services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Critere;
import tn.esprit.spring.missionentreprise.Repositories.CritereRepository;

import java.util.List;

@Service
@AllArgsConstructor

public class CritereService implements IServiceGenerique<Critere> {

    private CritereRepository critereRepository;

    @Override
    public Critere add(Critere critere) {
        if (critere.getNoteMaxCritere() < 0 || critere.getNoteMaxCritere() > 20) {
            throw new IllegalArgumentException("The note must be between 0 and 20");
        } //To check if note in the interval of 0 and 20
        return critereRepository.save(critere);
    }

    @Override
    public List<Critere> addAll(List<Critere> criteres) {
        return critereRepository.saveAll(criteres);
    }

    @Override
    public List<Critere> getAll() {
        return critereRepository.findAll();
    }

    @Override
    public Critere edit(Critere critere) {
        if (critere.getNoteMaxCritere() < 0 || critere.getNoteMaxCritere() > 20) {
            throw new IllegalArgumentException("The note must be between 0 and 20");
        }
        return critereRepository.save(critere);
    }

    @Override
    public List<Critere> editAll(List<Critere> criteres) {
        return critereRepository.saveAll(criteres);
    }

    @Override
    public void delete(Critere critere) {
        critereRepository.delete(critere);

    }

    @Override
    public void deleteAll() {
        critereRepository.deleteAll();

    }

    @Override
    public void deleteById(Long id) {
        critereRepository.deleteById(id);

    }

    @Override
    public Critere getById(Long id) {
        return critereRepository.findById(id)
                .orElse(Critere.builder()
                        .idCritere(0L)
                        .noteMaxCritere(20L)
                        .build() );
    }

    @Override
    public boolean existsById(Long id) {
        return critereRepository.existsById(id);
    }

    @Override
    public Long count() {
        return critereRepository.count();
    }



}
