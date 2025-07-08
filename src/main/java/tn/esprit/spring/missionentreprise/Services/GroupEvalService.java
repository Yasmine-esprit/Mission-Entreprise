package tn.esprit.spring.missionentreprise.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.GroupEval;
import tn.esprit.spring.missionentreprise.Repositories.GroupEvalRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupEvalService implements IServiceGenerique<GroupEval> {

    GroupEvalRepository groupEvalRepository;

    @Override
    public GroupEval add(GroupEval groupEval) {
        if (groupEval.getNoteGrp() < 0 || groupEval.getNoteGrp() > 20) {
            throw new IllegalArgumentException("The note must be between 0 and 20");
        }
        return groupEvalRepository.save(groupEval);
    }

    @Override
    public List<GroupEval> addAll(List<GroupEval> groupEvals) {
        groupEvals.forEach(ge -> {
            if (ge.getNoteGrp() < 0 || ge.getNoteGrp() > 20) {
                throw new IllegalArgumentException("All notes must be between 0 and 20");
            }
        });
        return groupEvalRepository.saveAll(groupEvals);
    }

    @Override
    public List<GroupEval> getAll() {
        return groupEvalRepository.findAll();
    }

    @Override
    public GroupEval edit(GroupEval groupEval) {
        if (groupEval.getNoteGrp() < 0 || groupEval.getNoteGrp() > 20) {
            throw new IllegalArgumentException("The note must be between 0 and 20");
        }
        return groupEvalRepository.save(groupEval);
    }

    @Override
    public List<GroupEval> editAll(List<GroupEval> groupEvals) {
        groupEvals.forEach(ge -> {
            if (ge.getNoteGrp() < 0 || ge.getNoteGrp() > 20) {
                throw new IllegalArgumentException("All notes must be between 0 and 20");
            }
        });
        return groupEvalRepository.saveAll(groupEvals);
    }

    @Override
    public void delete(GroupEval groupEval) {
        groupEvalRepository.delete(groupEval);
    }

    @Override
    public void deleteAll() {
        groupEvalRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        groupEvalRepository.deleteById(id);
    }

    @Override
    public GroupEval getById(Long id) {
        return groupEvalRepository.findById(id)
                .orElse(GroupEval.builder()
                        .noteGrpId(0L)
                        .build());
    }

    @Override
    public boolean existsById(Long id) {
        return groupEvalRepository.existsById(id);
    }

    @Override
    public Long count() {
        return groupEvalRepository.count();
    }


}