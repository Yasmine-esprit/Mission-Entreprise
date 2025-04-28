//Module Gestion Organisation

package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


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


}
