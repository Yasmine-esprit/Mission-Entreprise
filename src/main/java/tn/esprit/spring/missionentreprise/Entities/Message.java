
//Module Gestion User
package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;


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

    @Lob
    byte[] audioMessage; // Message vocal sous forme de fichier audio (si présent)

    @Lob
    byte[] videoMessage; // Message vidéo sous forme de fichier vidéo (si présent)

    @ManyToOne
    GroupeMsg groupeMsg;

    @ManyToOne(cascade = CascadeType.ALL)
    User userMessage;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private Set<Reaction> reactions; // Réactions associées à ce message
}
