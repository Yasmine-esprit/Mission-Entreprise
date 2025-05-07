//Module Gestion Evaluation
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


public class NoteTIndiv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long noteIndivId;

    float noteTIndiv;

    @ManyToOne
    GrilleEvaluation grilleEvaluation;

    @ManyToOne
    Enseignant enseignant;

    @ManyToOne
    Etudiant etudiant;

}