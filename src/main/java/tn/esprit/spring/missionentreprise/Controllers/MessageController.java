package tn.esprit.spring.missionentreprise.Controllers;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.Message;
import tn.esprit.spring.missionentreprise.Services.MessageService;

import java.util.Map;

@RestController
@RequestMapping("/messages/")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;



    @PostMapping("/envoyerMsg/{groupeid}")
    public ResponseEntity<?> envoyerMessage(@PathVariable ("groupeid") Long groupeId, @RequestBody Message message) throws MessagingException {

        return messageService.envoyerMessage(groupeId,message);
    }

    @GetMapping
    public ResponseEntity<?> getMessagesBygroup(){
        return messageService.getMessagesGroupedByUserGroups();
    }

    @GetMapping("/messages/group/{groupId}")
    public ResponseEntity<?> getMessagesByGroupId(@PathVariable Long groupId) {
        return messageService.getMessagesByid(groupId);
    }

    @DeleteMapping("{groupId}")
    public ResponseEntity<?> deleteMessages(@PathVariable Long groupId) {
        try {
            messageService.deleteById(groupId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
