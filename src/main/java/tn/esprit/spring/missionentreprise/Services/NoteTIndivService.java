package tn.esprit.spring.missionentreprise.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.NoteTIndiv;
import tn.esprit.spring.missionentreprise.Repositories.NoteTIndivRepository;

import java.util.List;

@Service
@AllArgsConstructor

public class NoteTIndivService implements IServiceGenerique<NoteTIndiv> {

    NoteTIndivRepository noteTIndivRepository;

    @Override
    public NoteTIndiv add(NoteTIndiv noteTIndiv) {
        if (noteTIndiv.getNoteTIndiv() >= 20 || noteTIndiv.getNoteTIndiv() <= 0) {
            throw new IllegalArgumentException("The note must be between 0 and 20");
        }
        return noteTIndivRepository.save(noteTIndiv);
    }

    @Override
    public List<NoteTIndiv> addAll(List<NoteTIndiv> noteTIndivList) {
        return noteTIndivRepository.saveAll(noteTIndivList);
    }

    @Override
    public List<NoteTIndiv> getAll() {
        return noteTIndivRepository.findAll();
    }

    @Override
    public NoteTIndiv edit(NoteTIndiv noteTIndiv) {
        if (noteTIndiv.getNoteTIndiv() >= 20 || noteTIndiv.getNoteTIndiv() <= 0) {
            throw new IllegalArgumentException("The note must be between 0 and 20");
        }
        return noteTIndivRepository.save(noteTIndiv);
    }

    @Override
    public List<NoteTIndiv> editAll(List<NoteTIndiv> noteTIndivList) {
        return noteTIndivRepository.saveAll(noteTIndivList);
    }

    @Override
    public void delete(NoteTIndiv noteTIndiv) {
        noteTIndivRepository.delete(noteTIndiv);
    }

    @Override
    public void deleteAll() {
        noteTIndivRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        noteTIndivRepository.deleteById(id);
    }

    @Override
    public NoteTIndiv getById(Long id) {
        return noteTIndivRepository.findById(id)
                .orElse(NoteTIndiv.builder()
                        .noteIndivId(0L)
                        .build());
    }

    @Override
    public boolean existsById(Long id) {
        return noteTIndivRepository.existsById(id);
    }

    @Override
    public Long count() {
        return noteTIndivRepository.count();
    }
}
