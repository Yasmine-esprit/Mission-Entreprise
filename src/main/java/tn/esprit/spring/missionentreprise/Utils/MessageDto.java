package tn.esprit.spring.missionentreprise.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Getter
 @Setter
public class MessageDto {
    private Long idMsg;
    private String contenu;
    private LocalDate dateEnvoi;
    private Boolean lu;
    private Long groupId;
    private Long senderId;
    private String senderName;
}
