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


public class IndivEval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long indivEvalId;


    float noteTIndiv;


    @ManyToOne
            Critere critere;

    float noteIndiv;

    @OneToOne
    GrilleEvaluation grilleEvaluation;

    @ManyToOne
    Etudiant etudiant;

    String generalComments;

}