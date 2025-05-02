package tn.esprit.spring.missionentreprise.Services;

import tn.esprit.spring.missionentreprise.Entities.Message;

import java.util.List;

public class MessageService implements IServiceGenerique<Message> {
    @Override
    public Message add(Message message) {
        return null;
    }

    @Override
    public List<Message> addAll(List<Message> t) {
        return List.of();
    }

    @Override
    public List<Message> getAll() {
        return List.of();
    }

    @Override
    public Message edit(Message message) {
        return null;
    }

    @Override
    public List<Message> editAll(List<Message> t) {
        return List.of();
    }

    @Override
    public void delete(Message message) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Message getById(Long id) {
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
