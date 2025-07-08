//Module Gestion de l'espace collaboratif

package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @Column(name="Nom Groupe", nullable = false)
    String nomGroupe;
    Date DateCreation;
    Visibilite visibilite;

    @OneToOne
    Projet projet;

    @OneToMany(mappedBy = "groupe")
    @com.fasterxml.jackson.annotation.JsonManagedReference
    List<Etudiant> etudiants;

    @OneToMany(mappedBy = "groupe")
    List<NoteTGrp> noteTGrps;

    @OneToOne(mappedBy = "groupe")
    Repo repo;






}
