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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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

public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idTheme;

    @NotNull
    String titreTheme;

    @NotNull
    String description;

    @ManyToOne
    Module module;

    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL, orphanRemoval = true)
    List <Projet> projets = new ArrayList<>();


=======
@Entity
@Getter @Setter @ToString(exclude = {"module","classe","groupe","projets"})
@NoArgsConstructor @AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Theme {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idTheme;

    @NotNull String titreTheme;
    @NotNull String description;

    /* ——— relations coupées pour éviter la récursion ——— */
    @JsonIgnore
    @ManyToOne
    Module module;

    @JsonIgnore
    @ManyToOne @JoinColumn(name = "classe_id")
    Classe classe;

    @JsonIgnore
    @ManyToOne @JoinColumn(name = "groupe_id")
    Groupe groupe;

    @JsonIgnore
    @OneToMany(mappedBy = "theme",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    List<Projet> projets = new ArrayList<>();
>>>>>>> ceadf4d (test)
}
