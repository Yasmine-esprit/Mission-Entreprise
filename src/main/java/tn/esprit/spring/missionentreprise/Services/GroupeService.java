package tn.esprit.spring.missionentreprise.Services;

import tn.esprit.spring.missionentreprise.Entities.Groupe;

import java.util.List;

public class GroupeService implements IServiceGenerique<Groupe> {
    @Override
    public Groupe add(Groupe groupe) {
        return null;
    }

    @Override
    public List<Groupe> addAll(List<Groupe> t) {
        return List.of();
    }

    @Override
    public List<Groupe> getAll() {
        return List.of();
    }

    @Override
    public Groupe edit(Groupe groupe) {
        return null;
    }

    @Override
    public List<Groupe> editAll(List<Groupe> t) {
        return List.of();
    }

    @Override
    public void delete(Groupe groupe) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Groupe getById(Long id) {
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
