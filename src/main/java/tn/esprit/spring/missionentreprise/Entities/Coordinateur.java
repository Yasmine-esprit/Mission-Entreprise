package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "coordinateur")
public class Coordinateur extends User {

    private String departement;
    private String anneeExperience;

}
