package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "classe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idClasse;

    String nomClasse;

    @ManyToOne
    @JoinColumn(name = "departement_id") // facultatif mais recommandé
    Departement departement;

    @ManyToOne
    @JoinColumn(name = "niveau_id") // facultatif mais recommandé
    Niveau niveau;

    // Tu peux ajouter d'autres champs ou relations si besoin (étudiants, groupes, etc.)
}
