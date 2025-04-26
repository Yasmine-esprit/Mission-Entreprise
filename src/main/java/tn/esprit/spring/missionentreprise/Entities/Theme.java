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
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTheme;

    @org.jetbrains.annotations.NotNull
    private String titreTheme;

    @NotNull
    private String description;

    private Date dateCreation;

    @OneToMany(mappedBy = "theme")
    private List<Projet> projets;
}
