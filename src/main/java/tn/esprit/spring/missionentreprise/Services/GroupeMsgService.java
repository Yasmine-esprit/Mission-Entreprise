package tn.esprit.spring.missionentreprise.Services;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.missionentreprise.Entities.GroupAssociation;
import tn.esprit.spring.missionentreprise.Entities.GroupeMsg;
import tn.esprit.spring.missionentreprise.Entities.Message;
import tn.esprit.spring.missionentreprise.Entities.User;
import tn.esprit.spring.missionentreprise.Repositories.GroupeMsgRepository;
import tn.esprit.spring.missionentreprise.Repositories.MessageRepository;
import tn.esprit.spring.missionentreprise.Repositories.UserRepository;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupeMsgService implements IServiceGenerique<GroupeMsg> {

    private final GroupeMsgRepository groupeMsgRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;


    public ResponseEntity<?> creeGroupeMsg(Set<Long> userIds) {

        System.out.println("user ids " + userIds);

        // 1. Récupérer utilisateur connecté
        String emailUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmailUser(emailUser)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        System.out.println("Utilisateur connecté : " + currentUser.getFullName() + " " + currentUser.getIdUser());

        // 2. Ajouter utilisateur connecté à la liste
        userIds.add(currentUser.getIdUser());

        Set<User> users = new HashSet<>(userRepository.findAllById(userIds));

        System.out.println("📋 Membres du groupe :");
        users.forEach(user -> System.out.println("👤 " + user.getIdUser() + " - " + user.getEmailUser()));
        System.out.println("Total utilisateurs : " + users.size());

        // 3. Créer le groupe sans lier les utilisateurs tout de suite
        GroupeMsg groupeMsg = GroupeMsg.builder()
                .imageGroupe(null)
                .users(new HashSet<>()) // on vide pour l'instant
                .nbrePersonne(users.size() == 2 ? GroupAssociation.ONE_TO_ONE : GroupAssociation.MANY_TO_MANY)
                .build();

        // 4. Sauvegarder d'abord le groupe pour obtenir son ID
        groupeMsg = groupeMsgRepository.save(groupeMsg); // <- Important !

        // 5. Lier les utilisateurs au groupe et vice versa
        for (User user : users) {
            user.getGroups().add(groupeMsg);     // relation User → Groupe
            groupeMsg.getUsers().add(user);      // relation Groupe → User
        }

        // 6. Sauvegarder les relations bidirectionnelles
        groupeMsgRepository.save(groupeMsg);     // MàJ des utilisateurs du groupe
        userRepository.saveAll(users);           // MàJ des groupes de chaque utilisateur

        return new ResponseEntity<>(groupeMsg, HttpStatus.OK); // tu peux aussi renvoyer le groupe ici
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

    @Transactional
    @Override
    public void deleteById(Long id) {
        GroupeMsg groupeMsg = groupeMsgRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        log.info("you are in delete now");
        // Charger les messages et utilisateurs
        groupeMsg.getMessages().size();
        groupeMsg.getUsers().size();

        // Supprimer explicitement les messages
        messageRepository.deleteAll(groupeMsg.getMessages());
        groupeMsg.getMessages().clear();

        // Rompre la relation User <-> GroupeMsg
        Set<User> users = new HashSet<>(groupeMsg.getUsers());
        for (User user : users) {
            user.getGroups().remove(groupeMsg);
        }
        groupeMsg.getUsers().clear();

        userRepository.saveAll(users);
        groupeMsgRepository.save(groupeMsg);

        // Supprimer le groupe
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
