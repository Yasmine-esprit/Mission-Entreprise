package tn.esprit.spring.missionentreprise.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import tn.esprit.spring.missionentreprise.Entities.Message;
import tn.esprit.spring.missionentreprise.Services.MessageService;
import tn.esprit.spring.missionentreprise.Utils.MessageDto;

@Controller
@RequiredArgsConstructor
public class WsChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/chat.sendMessage")
    public void receiveAndSendMessage(@Payload Message message) {
        // 1. Sauvegarder le message avec l'utilisateur
        Message saved = messageService.saveMessageWithPayloadUser(
                message.getGroupeMsg().getIdGrpMsg(),
                message
        );

        // 2. Convertir vers DTO
        MessageDto dto = new MessageDto();
        dto.setIdMsg(saved.getIdMsg());
        dto.setContenu(saved.getContenu());
        dto.setDateEnvoi(saved.getDateEnvoi());
        dto.setLu(saved.getLu());
        dto.setGroupId(saved.getGroupeMsg().getIdGrpMsg());

        if (saved.getUserMessage() != null) {
            dto.setSenderId(saved.getUserMessage().getIdUser());
            dto.setSenderName(saved.getUserMessage().getNomUser() + " " + saved.getUserMessage().getPrenomUser());
        }

        // 3. Broadcast le DTO propre (pas l’entité JPA !)
        messagingTemplate.convertAndSend("/topic/group/" + dto.getGroupId(), dto);
    }
}
