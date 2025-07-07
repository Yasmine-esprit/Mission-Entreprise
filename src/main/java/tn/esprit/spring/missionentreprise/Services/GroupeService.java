//Espace collaboratif
package tn.esprit.spring.missionentreprise.Services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Etudiant;
import tn.esprit.spring.missionentreprise.Entities.Groupe;
import tn.esprit.spring.missionentreprise.Entities.User;
import tn.esprit.spring.missionentreprise.Entities.roleName;
import tn.esprit.spring.missionentreprise.Repositories.GroupeRepository;
import tn.esprit.spring.missionentreprise.Repositories.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupeService implements IServiceGenerique<Groupe> {


    private GroupeRepository groupeRepository;
    private UserRepository userRepository;

    @Transactional
    public Groupe createGroupWithStudentsByEmail(Groupe groupe, Set<String> studentEmails) {
        Groupe savedGroupe = groupeRepository.save(groupe);

        List<User> potentialStudents = userRepository.findByRoleTypeAndEnabledAndNotLocked(roleName.ETUDIANT);

        List<Etudiant> selectedStudents = potentialStudents.stream()
                .filter(user -> studentEmails.contains(user.getEmailUser()))
                .map(user -> {
                    Etudiant etudiant = (Etudiant) user;
                    etudiant.setGroupe(savedGroupe);
                    return etudiant;
                })
                .collect(Collectors.toList());

        if (selectedStudents.size() != studentEmails.size()) {
            throw new IllegalArgumentException("Certains emails ne correspondent pas à des étudiants valides");
        }
        savedGroupe.setEtudiants(selectedStudents);

        return groupeRepository.save(savedGroupe);
    }


    @Override
    public Groupe add(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    @Override
    public List<Groupe> addAll(List<Groupe> groupes) {
        return groupeRepository.saveAll(groupes);
    }

    @Override
    public List<Groupe> getAll() {
        return groupeRepository.findAll();
    }

    @Override
    public Groupe edit(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    @Override
    public List<Groupe> editAll(List<Groupe> groupes) {
        return groupeRepository.saveAll(groupes);
    }

    @Override
    public void delete(Groupe groupe) {
        groupeRepository.delete(groupe);
    }

    @Override
    public void deleteAll() {
        groupeRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        groupeRepository.deleteById(id);
    }

    @Override
    public Groupe getById(Long id) {
        return groupeRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return groupeRepository.existsById(id);
    }

    @Override
    public Long count() {
        return groupeRepository.count();
    }
}
