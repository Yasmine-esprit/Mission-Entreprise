//Module Gestion Organisation

package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

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
}
