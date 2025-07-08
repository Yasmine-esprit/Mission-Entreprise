//Module Gestion User

package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;
import lombok.experimental.SuperBuilder;



@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Enseignant extends User{
    String grade;
    String demainRecherche; // Ex: L3, M1, M2
    String Bureau; // Info, GTR, etc.
    String specialite;

    @OneToMany(mappedBy = "enseignant")
    List <Projet> projets;

    @OneToMany(mappedBy = "enseignant")
    List <GrilleEvaluation> grilleEvaluations;

}
