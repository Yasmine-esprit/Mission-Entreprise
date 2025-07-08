
package tn.esprit.spring.missionentreprise.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

public class Niveau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idNiveau;
    String nomNiveau;

    @OneToMany(mappedBy = "niveau")
    List <Classe> classes;

    @OneToMany(mappedBy = "niveau")
    List <Module> modules;


}
