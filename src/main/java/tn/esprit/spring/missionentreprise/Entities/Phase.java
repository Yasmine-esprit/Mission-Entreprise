package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPhase;

    @NotNull
    private String titrePhase;

    @NotNull
    private String description;

    private Date dateDebut;
    private Date dateFin;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    @ManyToOne
    private Projet projet;

    @OneToMany(mappedBy = "phase")
    private List<Tache> taches;
}
