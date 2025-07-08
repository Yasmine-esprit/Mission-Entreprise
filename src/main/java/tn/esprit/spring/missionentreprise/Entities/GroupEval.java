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


public class GroupEval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long noteGrpId;

    @ManyToOne
    Critere critere;

    float noteGrp;

    @OneToOne
    GrilleEvaluation grilleEvaluation;

    @ManyToOne
    Etudiant etudiant;

    String comments;

    String generalComments;
}
