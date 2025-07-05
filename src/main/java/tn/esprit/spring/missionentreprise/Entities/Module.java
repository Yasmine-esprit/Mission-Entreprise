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
=======
@Entity
@Getter
@Setter
@ToString(exclude = {"niveau", "phases", "themes"})   // évite les toString récursifs
>>>>>>> ceadf4d (test)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
<<<<<<< HEAD

public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idModule;
    String nomModule;

    @ManyToOne
    Niveau niveau;

    @OneToMany(mappedBy = "module")
    List <Phase> phases;

    @OneToMany(mappedBy = "module")
    List <Theme> themes;
=======
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idModule;

    String nomModule;

    /* --------- relations coupées pour la sérialisation --------- */
    @JsonIgnore
    @ManyToOne
    Niveau niveau;

    @JsonIgnore
    @OneToMany(mappedBy = "module")
    List<Phase> phases;

    @JsonIgnore
    @OneToMany(mappedBy = "module")
    List<Theme> themes;
>>>>>>> ceadf4d (test)
}
