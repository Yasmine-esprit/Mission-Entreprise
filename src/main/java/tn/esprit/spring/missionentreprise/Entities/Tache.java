package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Getter
@Setter
@Entity
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTache;

    @NotNull
    private String titreTache;

    @NotNull
    private String description;

    private Date dateDebut;
    private Date dateFin;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    @ManyToOne
    private Phase phase;
}
