package tn.esprit.spring.missionentreprise.Services;
import lombok.RequiredArgsConstructor;
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
import tn.esprit.spring.missionentreprise.Repositories.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupeMsgService implements IServiceGenerique<GroupeMsg> {

    private final GroupeMsgRepository groupeMsgRepository;
    private final UserRepository userRepository;


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
        GroupeMsg groupeMsg = groupeMsgRepository.findByIdGrpMsg(id)
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + id));

        // Copier la liste pour éviter ConcurrentModificationException
        Set<User> users = new HashSet<>(groupeMsg.getUsers());
        System.out.println("user ids " + users);
        // Rompre les associations dans les deux sens
        for (User user : users) {
            System.out.println("user gr " + user.getGroups());
            user.getGroups().remove(groupeMsg); // côté User

        }


        groupeMsg.getUsers().clear(); // côté GroupeMsg

        userRepository.saveAll(users);           // Sauvegarde utilisateurs modifiés
        groupeMsgRepository.save(groupeMsg);     // Sauvegarde groupe sans utilisateurs

        // Maintenant que plus aucune relation n'existe, on peut le supprimer
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
