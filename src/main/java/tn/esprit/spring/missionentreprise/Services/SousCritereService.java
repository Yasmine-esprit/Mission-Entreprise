package tn.esprit.spring.missionentreprise.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.SousCritere;
import tn.esprit.spring.missionentreprise.Repositories.SousCritereRepository;

import java.util.List;

@Service
@AllArgsConstructor

public class SousCritereService implements IServiceGenerique<SousCritere> {

    SousCritereRepository sousCritereRepository;

    @Override
    public SousCritere add(SousCritere sousCritere) {
        if (sousCritere.getNoteMax() >= 20 || sousCritere.getNoteMin() <= 0) {
            throw new IllegalArgumentException("The note must be between 0 and 20");
        }
        return sousCritereRepository.save(sousCritere);
    }

    @Override
    public List<SousCritere> addAll(List<SousCritere> sousCriteres) {
        return sousCritereRepository.saveAll(sousCriteres);
    }

    @Override
    public List<SousCritere> getAll() {
        return sousCritereRepository.findAll();
    }

    @Override
    public SousCritere edit(SousCritere sousCritere) {
        if (sousCritere.getNoteMax() >= 20 || sousCritere.getNoteMin() <= 0) {
            throw new IllegalArgumentException("The note must be between 0 and 20");
        }
        return sousCritereRepository.save(sousCritere);
    }

    @Override
    public List<SousCritere> editAll(List<SousCritere> sousCriteres) {
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
        return sousCritereRepository.findById(id).
                orElse(SousCritere.builder()
                        .idSousCritere(0L)
                        .descriptionSousCritere("NullSousCritere")
                        .noteMin(0L)
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
}
