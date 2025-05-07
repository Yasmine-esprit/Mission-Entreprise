package tn.esprit.spring.missionentreprise.Services;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.GroupAssociation;
import tn.esprit.spring.missionentreprise.Entities.GroupeMsg;
import tn.esprit.spring.missionentreprise.Entities.Message;
import tn.esprit.spring.missionentreprise.Entities.User;
import tn.esprit.spring.missionentreprise.Repositories.GroupeMsgRepository;
import tn.esprit.spring.missionentreprise.Repositories.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupeMsgService implements IServiceGenerique<GroupeMsg> {

    private final GroupeMsgRepository groupeMsgRepository;
    private final UserRepository userRepository;


    public ResponseEntity<?> creeGroupeMsg (Set<Long> userIds) {

        // 1. Récupération de l'utilisateur connecté
        String emailUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmailUser(emailUser)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
        System.out.println("user connect " + currentUser.getFullName() + " " + currentUser.getIdUser());
        // 2. Ajout de l'utilisateur connecté à la liste si absent
        userIds.add(currentUser.getIdUser());

        Set<User> users = new HashSet<>(userRepository.findAllById(userIds));

        System.out.println("📋 Liste des utilisateurs dans le groupe :");
        users.forEach(user -> System.out.println("👤 " + user.getIdUser() + " - " + user.getEmailUser()));
        System.out.println("size " + users.size());

        GroupeMsg groupeMsg = GroupeMsg.builder()
                .imageGroupe(null)
                .users(users)
                .nbrePersonne(users.size() == 2 ? GroupAssociation.ONE_TO_ONE : GroupAssociation.MANY_TO_MANY)
                .build();

        // Synchronisation relation bidirectionnelle
        users.forEach(user -> user.getGroups().add(groupeMsg));
        userRepository.saveAll(users);


        groupeMsgRepository.save(groupeMsg);


        return new ResponseEntity<>("Groupe Cree", HttpStatus.OK);

    }

    public ResponseEntity<?> getUserGroups() {
        try {
            // Get the email of the currently authenticated user
            String email = SecurityContextHolder.getContext().getAuthentication().getName();

            // Fetch the user from the database using the email
            User user = userRepository.findByEmailUser(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));


            // Get all the groups the user is part of
            Set<GroupeMsg> userGroups = user.getGroups(); // Assuming the `User` entity has a `Set<GroupeMsg>` for groups

            List<Map<String, Object>> result = userGroups.stream().map(group -> {
                Map<String, Object> map = new HashMap<>();
                map.put("idGrpMsg", group.getIdGrpMsg());
                map.put("imageGroupe", group.getImageGroupe());
                map.put("nbrePersonne", group.getNbrePersonne());
                return map;
            }).toList();
            // Return the groups
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public GroupeMsg add(GroupeMsg groupeMsg) {
        return groupeMsg;
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
        // Find the group by ID
        GroupeMsg groupeMsg = groupeMsgRepository.findByIdGrpMsg(id)
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + id));

        // Optionally, if you want to remove all the user-group associations first
        for (User user : groupeMsg.getUsers()) {
            user.getGroups().remove(groupeMsg);  // Remove the group from each user's groups
        }

        // Now, delete the group itself
        groupeMsgRepository.delete(groupeMsg);
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
