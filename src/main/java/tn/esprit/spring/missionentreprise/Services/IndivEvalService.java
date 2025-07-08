package tn.esprit.spring.missionentreprise.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.IndivEval;
import tn.esprit.spring.missionentreprise.Repositories.IndivEvalRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class IndivEvalService implements IServiceGenerique<IndivEval> {

    private final IndivEvalRepository indivEvalRepository;

    @Override
    public IndivEval add(IndivEval indivEval) {
        if (indivEval.getNoteIndiv() < 0 || indivEval.getNoteIndiv() > 20) {
            throw new IllegalArgumentException("The note must be between 0 and 20");
        }
        return indivEvalRepository.save(indivEval);
    }

    @Override
    public List<IndivEval> addAll(List<IndivEval> indivEvals) {
        indivEvals.forEach(ie -> {
            if (ie.getNoteIndiv() < 0 || ie.getNoteIndiv() > 20) {
                throw new IllegalArgumentException("All notes must be between 0 and 20");
            }
        });
        return indivEvalRepository.saveAll(indivEvals);
    }

    @Override
    public List<IndivEval> getAll() {
        return indivEvalRepository.findAll();
    }

    @Override
    public IndivEval edit(IndivEval indivEval) {
        if (indivEval.getNoteIndiv() < 0 || indivEval.getNoteIndiv() > 20) {
            throw new IllegalArgumentException("The note must be between 0 and 20");
        }
        return indivEvalRepository.save(indivEval);
    }

    @Override
    public List<IndivEval> editAll(List<IndivEval> indivEvals) {
        indivEvals.forEach(ie -> {
            if (ie.getNoteIndiv() < 0 || ie.getNoteIndiv() > 20) {
                throw new IllegalArgumentException("All notes must be between 0 and 20");
            }
        });
        return indivEvalRepository.saveAll(indivEvals);
    }

    @Override
    public void delete(IndivEval indivEval) {
        indivEvalRepository.delete(indivEval);
    }

    @Override
    public void deleteAll() {
        indivEvalRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        indivEvalRepository.deleteById(id);
    }

    @Override
    public IndivEval getById(Long id) {
        return indivEvalRepository.findById(id)
                .orElse(IndivEval.builder()
                        .indivEvalId(0L)
                        .build());
    }

    @Override
    public boolean existsById(Long id) {
        return indivEvalRepository.existsById(id);
    }

    @Override
    public Long count() {
        return indivEvalRepository.count();
    }



}