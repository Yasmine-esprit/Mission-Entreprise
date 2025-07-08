package tn.esprit.spring.missionentreprise.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.DescSubCriteria;
import tn.esprit.spring.missionentreprise.Entities.SousCritere;
import tn.esprit.spring.missionentreprise.Repositories.SousCritereRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SousCritereService implements IServiceGenerique<SousCritere> {

    private final SousCritereRepository sousCritereRepository;

    @Override
    public SousCritere add(SousCritere sousCritere) {
        validateSousCritere(sousCritere);
        return sousCritereRepository.save(sousCritere);
    }

    @Override
    public List<SousCritere> addAll(List<SousCritere> sousCriteres) {
        sousCriteres.forEach(this::validateSousCritere);
        return sousCritereRepository.saveAll(sousCriteres);
    }

    @Override
    public List<SousCritere> getAll() {
        return sousCritereRepository.findAll();
    }

    @Override
    public SousCritere edit(SousCritere sousCritere) {
        validateSousCritere(sousCritere);
        return sousCritereRepository.save(sousCritere);
    }

    @Override
    public List<SousCritere> editAll(List<SousCritere> sousCriteres) {
        sousCriteres.forEach(this::validateSousCritere);
        return sousCritereRepository.saveAll(sousCriteres);
    }

    @Override
    public void delete(SousCritere sousCritere) {
        sousCritereRepository.delete(sousCritere);
    }

    @Override
    public void deleteAll() {
        sousCritereRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        sousCritereRepository.deleteById(id);
    }

    @Override
    public SousCritere getById(Long id) {
        return sousCritereRepository.findById(id)
                .orElse(SousCritere.builder()
                        .idSousCritere(0L)
                        .nameSousCritere("Default Sub-Criteria")
                        .descSubCriteria(DescSubCriteria.FAIR)
                        .maxPoints(0L)
                        .noteMax(20L)
                        .build());
    }

    @Override
    public boolean existsById(Long id) {
        return sousCritereRepository.existsById(id);
    }

    @Override
    public Long count() {
        return sousCritereRepository.count();
    }

    // Additional business methods
    public List<SousCritere> findByNameContaining(String name) {
        return sousCritereRepository.findByNameSousCritereContainingIgnoreCase(name);
    }

    public List<SousCritere> findByMainCriteria(Long mainCriteriaId) {
        return sousCritereRepository.findByMainCritere_IdMainCritere(mainCriteriaId);
    }



    private void validateSousCritere(SousCritere sousCritere) {
        if (sousCritere.getNameSousCritere() == null || sousCritere.getNameSousCritere().isEmpty()) {
            throw new IllegalArgumentException("Sub-criteria name cannot be empty");
        }
        if (sousCritere.getDescSubCriteria() == null) {
            throw new IllegalArgumentException("Sub-criteria description cannot be empty");
        }
        if (sousCritere.getMaxPoints() == null || sousCritere.getMaxPoints() < 0) {
            throw new IllegalArgumentException("Max points must be positive");
        }
        if (sousCritere.getNoteMax() == null || sousCritere.getNoteMax() < 0 || sousCritere.getNoteMax() > 20) {
            throw new IllegalArgumentException("Note must be between 0 and 20");
        }
    }
}