
//Module Gestion User
package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idMsg ;
    String contenu;
    LocalDate dateEnvoi;
    Boolean lu;

    @ManyToOne
    GroupeMsg groupeMsg;

    @ManyToOne(cascade = CascadeType.ALL)
    User userMessage;
}
