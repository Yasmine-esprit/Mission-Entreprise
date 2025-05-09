//Module Gestion de l'espace collaboratif
package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProjet;
    @Column(name = "TitreProjet", nullable = false)
    private String titreProjet;
    @Column(name = "DescriptionProjet", nullable = false)
    @Size(min = 10, message = "La description du projet doit contenir au moins 10 caractères.")
    private String descriptionProjet;
    private LocalDate dateCreation;
    @Enumerated(EnumType.STRING)
    private tn.esprit.spring.missionentreprise.Entities.Statut statut;
    @Enumerated(EnumType.STRING)
    private tn.esprit.spring.missionentreprise.Entities.Visibilite visibilite;
//Associations

    @ManyToOne
    Theme theme;

    @OneToMany(mappedBy = "projet")
    List<Tache> taches;

    @OneToOne(mappedBy = "projet")
    Groupe groupe;

    @ManyToOne
    Enseignant enseignant;




    public String getTitreProjet() {
        return titreProjet;
    }

    public void setTitreProjet(String titreProjet) {
        this.titreProjet = titreProjet;
    }

    public String getDescriptionProjet() {
        return descriptionProjet;
    }

    public void setDescriptionProjet(String descriptionProjet) {
        this.descriptionProjet = descriptionProjet;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Visibilite getVisibilite() {
        return visibilite;
    }

    public void setVisibilite(Visibilite visibilite) {
        this.visibilite = visibilite;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public void setIdProjet(Long idProjet) {
        this.idProjet = idProjet;
    }

    public Long getIdProjet() {
        return idProjet;
    }
}
