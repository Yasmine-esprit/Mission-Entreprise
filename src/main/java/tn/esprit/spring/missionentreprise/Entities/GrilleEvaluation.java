// Module Gestion Evaluation

package tn.esprit.spring.missionentreprise.Entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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


public class GrilleEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idEvaluation;
    String nomEvaluation;
    LocalDate dateEvaluation;
    @Enumerated(EnumType.STRING)
    TypeGrilleEval typeEval;

    @OneToOne
    Phase phase;

    @OneToMany(mappedBy = "grilleEvaluation",  cascade = CascadeType.ALL, orphanRemoval = true)
    List<NoteTIndiv> noteIndiv;

    @OneToMany(mappedBy = "grilleEvaluation",  cascade = CascadeType.ALL, orphanRemoval = true)
    List<NoteTGrp> noteGrp;

    @OneToMany(mappedBy = "grilleEvaluation")
    List<Critere> criteres;
}
