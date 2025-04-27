//Module Gestion Depot

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

public class Interaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idInteraction;
    LocalDate dateInteraction;
    TypeInteraction typeInteraction;

    @ManyToOne
    Post post;

    @ManyToOne
    User user;
}
