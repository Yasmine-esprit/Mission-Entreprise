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


public class NoteTGrp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long noteGrpId;


    float noteTGrp;

    @ManyToOne
    GrilleEvaluation grilleEvaluation;

    @ManyToOne
    Groupe groupe;

    @ManyToOne
    Enseignant enseignant;

    @ManyToOne
    Etudiant etudiant;
}
