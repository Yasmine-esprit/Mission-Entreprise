package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "coordinateur")
public class Coordinateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCoor ;

    private String nomCoor ;
    private String prenomCoor ;
    private String departement;
    private String anneeExperience;

}
