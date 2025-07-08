package tn.esprit.spring.missionentreprise.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Critere;
import tn.esprit.spring.missionentreprise.Repositories.CritereRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CritereService implements IServiceGenerique<Critere> {

    private final CritereRepository critereRepository;

    @Override
    public Critere add(Critere critere) {
        validateCritere(critere);
        return critereRepository.save(critere);
    }

    @Override
    public List<Critere> addAll(List<Critere> criteres) {
        criteres.forEach(this::validateCritere);
        return critereRepository.saveAll(criteres);
    }

    @Override
    public List<Critere> getAll() {
        return critereRepository.findAll();
    }

    @Override
    public Critere edit(Critere critere) {
        validateCritere(critere);
        return critereRepository.save(critere);
    }

    @Override
    public List<Critere> editAll(List<Critere> criteres) {
        criteres.forEach(this::validateCritere);
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
                        .titreCritere("Default Critere")
                        .codeCritere("DEFAULT")
                        .descriptionCritere("Default description")
                        .TotalPoints(0f)
                        .build());
    }

    @Override
    public boolean existsById(Long id) {
        return critereRepository.existsById(id);
    }

    @Override
    public Long count() {
        return critereRepository.count();
    }

    private void validateCritere(Critere critere) {
        if (critere.getTitreCritere() == null || critere.getTitreCritere().isEmpty()) {
            throw new IllegalArgumentException("Critere title cannot be empty");
        }
        if (critere.getCodeCritere() == null || critere.getCodeCritere().isEmpty()) {
            throw new IllegalArgumentException("Critere code cannot be empty");
        }
        if (critere.getDescriptionCritere() == null || critere.getDescriptionCritere().isEmpty()) {
            throw new IllegalArgumentException("Critere description cannot be empty");
        }
        if (critere.getTotalPoints() < 0) {
            throw new IllegalArgumentException("Total points cannot be negative");
        }
    }
}