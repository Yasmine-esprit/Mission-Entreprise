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

public class Niveau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idNiveau;
    String nomNiveau;

    @OneToMany(mappedBy = "niveau")
    List <Classe> classes;

    @OneToMany(mappedBy = "niveau")
    List <Module> modules;
=======
@Entity
@Getter @Setter @ToString(exclude = {"classes","modules"})
@NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Niveau {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idNiveau;

    String nomNiveau;

    /* ——— relations coupées ——— */
    @JsonIgnore
    @OneToMany(mappedBy = "niveau")
    List<Classe> classes;

    @JsonIgnore
    @OneToMany(mappedBy = "niveau")
    List<Module> modules;
>>>>>>> ceadf4d (test)
}
