//Module Gestion Depot

package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Repo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idRepo;
    String nomRepo;

    @OneToOne
    Groupe groupe;

    @OneToMany(mappedBy = "repo")
    List <Commit> commits;

    @OneToOne
    SousTache sousTache;

}
