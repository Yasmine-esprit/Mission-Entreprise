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
    Boolean choixEffectue = false;

    @ManyToOne
    Classe classe;

    @ManyToOne
    Groupe groupe;
    
    @ManyToOne
    @JoinColumn(name = "theme_choisi_id")
    Theme themeChoisi;

    @OneToMany(mappedBy = "etudiant")
    List<Tache> taches;


    @OneToMany(mappedBy = "etudiant")
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> 09d40dcbf742febe9f850db30ba0e4a451c1a52e
    List <IndivEval> noteIndiv;

    @OneToMany(mappedBy = "etudiant")
    List <GroupEval> groupEvals;
<<<<<<< HEAD
=======
    List <NoteTIndiv> noteTIndiv;


>>>>>>> 800784042b3a6f6955d33992fcb8e5a432132e7f
=======

>>>>>>> 09d40dcbf742febe9f850db30ba0e4a451c1a52e
}
