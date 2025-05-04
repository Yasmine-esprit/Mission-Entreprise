package tn.esprit.spring.missionentreprise.Services;

import tn.esprit.spring.missionentreprise.Entities.Groupe;
import tn.esprit.spring.missionentreprise.Entities.GroupeMsg;

import java.util.List;

public class GroupeService implements IServiceGenerique<GroupeMsg> {

    @Override
    public GroupeMsg add(GroupeMsg groupeMsg) {
        return null;
    }

    @Override
    public List<GroupeMsg> addAll(List<GroupeMsg> t) {
        return List.of();
    }

    @Override
    public List<GroupeMsg> getAll() {
        return List.of();
    }

    @Override
    public GroupeMsg edit(GroupeMsg groupeMsg) {
        return null;
    }

    @Override
    public List<GroupeMsg> editAll(List<GroupeMsg> t) {
        return List.of();
    }

    @Override
    public void delete(GroupeMsg groupeMsg) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public GroupeMsg getById(Long id) {
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public Long count() {
        return null;
    }
}
