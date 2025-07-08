//Module Gestion User

package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Etudiant extends User{
    String matricule;
    String niveau; // Ex: L3, M1, M2
    String specialite; // Info, GTR, etc.
    LocalDate dateNaissance;

    @ManyToOne
    Classe classe;

    @ManyToOne
    @com.fasterxml.jackson.annotation.JsonBackReference
    Groupe groupe;

    @OneToMany(mappedBy = "etudiant")
    List<Tache> taches;


    @OneToMany(mappedBy = "etudiant")
    List <NoteTIndiv> noteTIndiv;
}
