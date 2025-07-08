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
public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idGroupe;

    @Column(name = "nom_groupe", nullable = false)
    String nomGroupe;

    @Enumerated(EnumType.STRING)
    Visibilite visibilite;

    /* Relations */
    @OneToOne
    @JoinColumn(name = "projet_id")
    Projet projet;

    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL)
    List<Etudiant> etudiants;

    @ManyToOne
    GrilleEvaluation grilleEvaluation;

    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL)
    List<GroupEval> groupEvals;

    @OneToOne(mappedBy = "groupe", cascade = CascadeType.ALL)
    Repo repo;
}
