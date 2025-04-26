package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGroupe;
    @Column(name="Nom Groupe", nullable = false)
    private String nomGroupe;
    private Date DateCreation;
    private Visibilite visibilite;
    private String membres;

    @OneToMany(mappedBy = "groupe")
    private List<Projet> projets;



}
