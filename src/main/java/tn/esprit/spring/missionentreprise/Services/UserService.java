package tn.esprit.spring.missionentreprise.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.User;
import tn.esprit.spring.missionentreprise.Repositories.UserRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserService implements IServiceGenerique<User> {


    private final UserRepository userRepository;
    @Override
    public User add(User user) {
        return null;
    }

    @Override
    public List<User> addAll(List<User> t) {
        return List.of();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User edit(User user) {
        return null;
    }

    @Override
    public List<User> editAll(List<User> t) {
        return List.of();
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null) ;
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public Long count() {
        return userRepository.count();
    }
}
