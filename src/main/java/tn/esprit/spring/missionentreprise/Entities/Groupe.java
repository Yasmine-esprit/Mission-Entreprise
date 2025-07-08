package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
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

    //@Temporal(TemporalType.DATE)
    //Date dateCreation;

    @Enumerated(EnumType.STRING)
    private Visibilite visibilite;

    @OneToOne
    @JoinColumn(name = "projet_id")
    Projet projet;
    
    @ManyToOne
    @JoinColumn(name = "classe_id")
    Classe classe;

    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL)
    List<Etudiant> etudiants;

<<<<<<< HEAD
<<<<<<< HEAD
    @ManyToOne
    GrilleEvaluation grilleEvaluation;
=======
    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL)
    List<NoteTGrp> noteTGrps;
>>>>>>> 800784042b3a6f6955d33992fcb8e5a432132e7f
=======
    @ManyToOne
    GrilleEvaluation grilleEvaluation;

>>>>>>> 09d40dcbf742febe9f850db30ba0e4a451c1a52e

    @OneToOne(mappedBy = "groupe", cascade = CascadeType.ALL)
    Repo repo;
}
