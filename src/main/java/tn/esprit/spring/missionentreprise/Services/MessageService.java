package tn.esprit.spring.missionentreprise.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.missionentreprise.Entities.GroupeMsg;
import tn.esprit.spring.missionentreprise.Entities.Message;
import tn.esprit.spring.missionentreprise.Entities.User;
import tn.esprit.spring.missionentreprise.Repositories.GroupeMsgRepository;
import tn.esprit.spring.missionentreprise.Repositories.MessageRepository;
import tn.esprit.spring.missionentreprise.Repositories.UserRepository;
import tn.esprit.spring.missionentreprise.Utils.MessageDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService implements IServiceGenerique<Message> {

    /*
    La logique que nous allons développer prend en compte :

        Vérification de l'appartenance au groupe.

        Envoi d'un message à un groupe.

        Réception des messages d'un groupe.

        Nous allons donc avoir :

        Une logique pour envoyer des messages dans un groupe.

        Une logique pour récupérer les messages d'un groupe.
    * */
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final GroupeMsgRepository groupeMsgRepository;
  private final SimpMessagingTemplate messagingTemplate;

    //methode personnalisée
    public ResponseEntity<?> envoyerMessage(Long groupeId, Message message) {
        try {
            // 1. Vérification de l'existence du groupe
            GroupeMsg groupeMsg = groupeMsgRepository.findByIdGrpMsg(groupeId)
                    .orElseThrow(() -> new RuntimeException("Groupe non trouvé"));

            // 2. Récupérer l'utilisateur connecté
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByEmailUser(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // 3. Vérification de l'appartenance de l'utilisateur au groupe
            if (!groupeMsg.getUsers().contains(user)) {
                return new ResponseEntity<>("Utilisateur n'est pas un membre du groupe", HttpStatus.FORBIDDEN);
            }

            // 4. Créer et envoyer le message
            message.setGroupeMsg(groupeMsg); // Associate the message with the group
            Message sentMessage = add(message); // Save the message

            // Publish via WebSocket to subscribers on "/topic/messages"
            messagingTemplate.convertAndSend("/topic/messages", message);
            return new ResponseEntity<>("Message envoyé avec succès", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public ResponseEntity<?> getMessagesGroupedByUserGroups() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByEmailUser(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            Set<GroupeMsg> userGroups = user.getGroups();

            Map<Long, List<MessageDto>> groupedMessages = userGroups.stream()
                    .collect(Collectors.toMap(
                            GroupeMsg::getIdGrpMsg,
                            group -> group.getMessages().stream()
                                    .map(msg -> new MessageDto(
                                            msg.getIdMsg(),
                                            msg.getContenu(),
                                            msg.getDateEnvoi(),
                                            msg.getLu(),
                                            group.getIdGrpMsg(),
                                            msg.getUserMessage().getIdUser(),
                                            msg.getUserMessage().getNomUser() + " " + msg.getUserMessage().getPrenomUser()
                                    ))
                                    .toList()
                    ));

            return new ResponseEntity<>(groupedMessages, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public Message add(Message message) {
        // Ensure the sender (userMessage) is set
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmailUser(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Build and save the message
        Message msg = Message.builder()
                .contenu(message.getContenu())
                .dateEnvoi(LocalDate.now())
                .userMessage(user) // Set the sender (userMessage) to the authenticated user
                .audioMessage(message.getAudioMessage())
                .videoMessage(message.getVideoMessage())
                .groupeMsg(message.getGroupeMsg()) // Make sure the group is set
                .reactions(message.getReactions())
                .build();

        return messageRepository.save(msg);
    }

    // In MessageService.java

    @Transactional
    public Message saveMessage(Long groupeId, Message message) {
        // 1) fetch and set the GroupeMsg
        GroupeMsg groupe = groupeMsgRepository.findById(groupeId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found " + groupeId));
        message.setGroupeMsg(groupe);

        // 2) fetch/set the User from security context
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmailUser(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + email));
        message.setUserMessage(user);

        // 3) set date, etc. if needed
        message.setDateEnvoi(LocalDate.now());

        // 4) save and return
        return messageRepository.save(message);
    }

    @Transactional
    public Message saveMessageWithPayloadUser(Long groupeId, Message message) {
        // 1) fetch group
        GroupeMsg groupe = groupeMsgRepository.findById(groupeId
                )
                .orElseThrow(() -> new EntityNotFoundException("Group not found " + groupeId));
        message.setGroupeMsg(groupe);

        // 2) fetch user by payload id, not SecurityContext
        Long payloadUserId = message.getUserMessage().getIdUser();
        User user = userRepository.findById(payloadUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + payloadUserId));
        message.setUserMessage(user);

        // 3) set date
        message.setDateEnvoi(LocalDate.now());

        // 4) save
        return messageRepository.save(message);
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
        // Find the group by ID
        GroupeMsg groupeMsg = groupeMsgRepository.findByIdGrpMsg(id)
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + id));

        System.out.println(groupeMsg.getIdGrpMsg());

        // Get the messages for the group
        List<Message> msg = messageRepository.findByGroupeMsg(groupeMsg)
                .orElse(List.of());  // If no messages are found, return an empty list instead of throwing an exception

        // If there are messages, delete them
        if (!msg.isEmpty()) {
            for (Message m : msg) {
                messageRepository.deleteById(m.getIdMsg());
            }
        } else {
            System.out.println("No messages to delete for group ID: " + id);
        }
    }


    @Override
    public Message getById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public Long count() {
        return null;
    }

    public ResponseEntity<?> getMessagesByid(Long groupId) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByEmailUser(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // Check if the user is part of the requested group
            Optional<GroupeMsg> groupOpt = user.getGroups().stream()
                    .filter(g -> g.getIdGrpMsg().equals(groupId))
                    .findFirst();

            if (groupOpt.isEmpty()) {
                return new ResponseEntity<>("Vous n'avez pas accès à ce groupe", HttpStatus.FORBIDDEN);
            }

            GroupeMsg group = groupOpt.get();

            // Map the messages into a list of MessageDto
            List<MessageDto> messages = group.getMessages().stream()
                    .map(msg -> new MessageDto(
                            msg.getIdMsg(),
                            msg.getContenu(),
                            msg.getDateEnvoi(),
                            msg.getLu(),
                            group.getIdGrpMsg(),
                            msg.getUserMessage().getIdUser(),
                            msg.getUserMessage().getNomUser() + " " + msg.getUserMessage().getPrenomUser()
                    ))
                    .toList();

            return new ResponseEntity<>(messages, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
