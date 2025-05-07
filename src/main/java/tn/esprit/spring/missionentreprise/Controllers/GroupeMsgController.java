package tn.esprit.spring.missionentreprise.Controllers;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.GroupeMsg;
import tn.esprit.spring.missionentreprise.Entities.Message;
import tn.esprit.spring.missionentreprise.Services.GroupeMsgService;

import java.util.Set;

@RestController
@RequestMapping("/groupes/")
@RequiredArgsConstructor
public class GroupeMsgController {
    private final GroupeMsgService groupeMsgService;

    @PostMapping("/creeGroupe")
    public ResponseEntity<?> creerGroupe(@RequestBody Set<Long> userIds) {
        try {
            System.out.println("dans le controller");

            return groupeMsgService.creeGroupeMsg(userIds);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getGroups")
    public ResponseEntity<?> getGroups() {
        return groupeMsgService.getUserGroups();
    }

    @DeleteMapping("{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupId) {
        try {
            groupeMsgService.deleteById(groupId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
