// Module Gestion Organisation

package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idCLasse;

    String nomClasse;

    @ManyToOne
    Niveau niveau;

    @OneToMany(mappedBy = "classe")
    List<Etudiant> etudiants;

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    List<Groupe> groupes;

    @ManyToOne
    GrilleEvaluation grilleEvaluation;
}
