package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idReaction;

    String emoji; // Emoji utilisé pour la réaction (ex : '👍', '❤️', etc.)

    @ManyToOne
    Message message; // Message auquel cette réaction appartient

    @ManyToOne
    User user; // Utilisateur qui a réagi au message
}
