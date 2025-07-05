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
<<<<<<< HEAD
=======
    Boolean choixEffectue = false;
>>>>>>> ceadf4d (test)

    @ManyToOne
    Classe classe;

    @ManyToOne
    Groupe groupe;
<<<<<<< HEAD
=======
    
    @ManyToOne
    @JoinColumn(name = "theme_choisi_id")
    Theme themeChoisi;
>>>>>>> ceadf4d (test)

    @OneToMany(mappedBy = "etudiant")
    List<Tache> taches;


    @OneToMany(mappedBy = "etudiant")
    List <NoteTIndiv> noteTIndiv;
}
