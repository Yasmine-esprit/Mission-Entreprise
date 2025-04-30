
//Module Gestion de l'espace collaboratif

package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class SousTache {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idSousTache;

    @NotNull
    String titreSousTache;

    @NotNull
    String descriptionSousTache;

    LocalDate dateDebut;
    LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    Statut statut;

    @ManyToOne
    Tache tache;
}
