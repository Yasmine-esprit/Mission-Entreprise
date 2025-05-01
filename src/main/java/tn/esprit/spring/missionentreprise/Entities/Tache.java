//Module Gestion de l'espace collaboratif

package tn.esprit.spring.missionentreprise.Entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idTache;

    @NotNull
    String titreTache;

    @NotNull
    String descriptionTache;

    LocalDate dateDebut;
    LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    Statut statut;

    @ManyToOne
    Projet projet;

    @ManyToOne
    Phase phase;

    @ManyToOne
    Etudiant etudiant;

    @OneToMany(mappedBy = "tache")
    List<SousTache> sousTaches;
}
