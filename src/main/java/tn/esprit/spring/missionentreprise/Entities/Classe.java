<<<<<<< HEAD
//Module Gestion Organisation

package tn.esprit.spring.missionentreprise.Entities;

=======
package tn.esprit.spring.missionentreprise.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
>>>>>>> ceadf4d (test)
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

<<<<<<< HEAD

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idCLasse;
    String nomClasse;

    @ManyToOne
    Niveau niveau;

    @OneToMany(mappedBy = "classe")
    List<Etudiant> etudiants;
=======
@Entity
@Getter @Setter @ToString(exclude = {"niveau","departement","etudiants","groupes"})
@NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Classe {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idCLasse;

    String nomClasse;

    /* ——— relations coupées ——— */
    @JsonIgnore
    @ManyToOne
    Niveau niveau;

    @JsonIgnore
    @ManyToOne @JoinColumn(name = "departement_id")
    Departement departement;

    @JsonIgnore
    @OneToMany(mappedBy = "classe")
    List<Etudiant> etudiants;

    @JsonIgnore
    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    List<Groupe> groupes;
>>>>>>> ceadf4d (test)
}
