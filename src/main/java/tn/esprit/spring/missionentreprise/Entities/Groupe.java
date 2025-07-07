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

    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL)
    List<Etudiant> etudiants;

    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL)
    List<NoteTGrp> noteTGrps;

    @OneToOne(mappedBy = "groupe", cascade = CascadeType.ALL)
    Repo repo;
}
