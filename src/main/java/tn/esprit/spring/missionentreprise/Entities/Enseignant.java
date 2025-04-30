package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "ensignant")
public class Enseignant extends User{

    private String grade;
    private String demainRecherche; // Ex: L3, M1, M2
    private String Bureau; // Info, GTR, etc.

}
