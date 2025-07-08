// Module Gestion Evaluation

package tn.esprit.spring.missionentreprise.Entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Column(nullable = false)
    String teacher;
    String nomEvaluation;
    LocalDate dateEvaluation;
    @Enumerated(EnumType.STRING)
    TypeGrilleEval typeEval;


    @OneToOne
    Phase phase;

    @ManyToOne
    Enseignant enseignant;

    @OneToMany(mappedBy = "grilleEvaluation")
    List <Groupe> groupes = new ArrayList<>();

    @OneToMany(mappedBy = "grilleEvaluation")
    List <Classe> classes = new ArrayList<>();

    @OneToMany(mappedBy = "grilleEvaluation")
    List<Critere> criteres;


}
