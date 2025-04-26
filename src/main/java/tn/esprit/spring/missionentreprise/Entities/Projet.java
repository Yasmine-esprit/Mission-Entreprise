package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProjet;
    @Column(name = "TitreProjet", nullable = false)
    private String titreProjet;
    @Column(name = "DescriptionProjet", nullable = false)
    //@Size(min = 10, message = "La description du projet doit contenir au moins 10 caractères.")
    private String descriptionProjet;
    private Date dateCreation;
    @Enumerated(EnumType.STRING)
    private Statut statut;
    @Enumerated(EnumType.STRING)
    private Visibilite visibilite;
//Associations

    @ManyToOne
    private Theme theme;

    @OneToMany(mappedBy = "projet")
    private List<Phase> phases;

    @ManyToOne
    private Groupe groupe;

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

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
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
