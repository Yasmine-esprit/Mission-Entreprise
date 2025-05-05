package tn.esprit.spring.missionentreprise.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.NoteTGrp;
import tn.esprit.spring.missionentreprise.Repositories.NoteTGrpRepository;

import java.util.List;

@Service
@AllArgsConstructor

public class NoteTGrpService implements IServiceGenerique<NoteTGrp> {

    NoteTGrpRepository noteTGrpRepository;

    @Override
    public NoteTGrp add(NoteTGrp noteTGrp) {
        if (noteTGrp.getNoteTGrp() >= 20 || noteTGrp.getNoteTGrp() <= 0) {
            throw new IllegalArgumentException("The note must be between 0 and 20");
        }
        return noteTGrpRepository.save(noteTGrp);
    }

    @Override
    public List<NoteTGrp> addAll(List<NoteTGrp> noteTGrpList) {
        return noteTGrpRepository.saveAll(noteTGrpList);
    }

    @Override
    public List<NoteTGrp> getAll() {
        return noteTGrpRepository.findAll();
    }

    @Override
    public NoteTGrp edit(NoteTGrp noteTGrp) {
        if (noteTGrp.getNoteTGrp() >= 20 || noteTGrp.getNoteTGrp() <= 0) {
            throw new IllegalArgumentException("The note must be between 0 and 20");
        }
        return noteTGrpRepository.save(noteTGrp);
    }

    @Override
    public List<NoteTGrp> editAll(List<NoteTGrp> noteTGrpList) {
        return noteTGrpRepository.saveAll(noteTGrpList);
    }

    @Override
    public void delete(NoteTGrp noteTGrp) {
        noteTGrpRepository.delete(noteTGrp);
    }

    @Override
    public void deleteAll() {
        noteTGrpRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        noteTGrpRepository.deleteById(id);
    }

    @Override
    public NoteTGrp getById(Long id) {
        return noteTGrpRepository.findById(id)
                .orElse(NoteTGrp.builder()
                        .noteGrpId(0L)
                        .build());
    }

    @Override
    public boolean existsById(Long id) {
        return noteTGrpRepository.existsById(id);
    }

    @Override
    public Long count() {
        return noteTGrpRepository.count();
    }
}
