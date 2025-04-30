//Module Gestion Depot
package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Commit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idCommit;
    String titreCommit;
    LocalDateTime dateCommit;
    @ManyToOne
    Repo repo;
}
