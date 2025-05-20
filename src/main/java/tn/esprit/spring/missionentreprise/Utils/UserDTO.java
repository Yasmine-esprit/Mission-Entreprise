package tn.esprit.spring.missionentreprise.Utils;

import jakarta.persistence.Lob;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    Long idUser;
    String nomUser;
    String prenomUser;
    String emailUser;
    boolean enabledUser;
    boolean accountLockedUser;
    List<String> roles;
    @Lob
    byte[] photoProfil;


}
