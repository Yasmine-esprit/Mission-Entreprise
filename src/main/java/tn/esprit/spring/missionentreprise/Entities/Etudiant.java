package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "etudiant")
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEtud;

    private String nom;
    private String prenom;
    private String matricule;
    private String niveau; // Ex: L3, M1, M2
    private String specialite; // Info, GTR, etc.
    private LocalDate dateNaissance;
}
