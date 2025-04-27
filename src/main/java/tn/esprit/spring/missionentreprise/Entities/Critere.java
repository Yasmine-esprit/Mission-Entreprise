// Module Gestion Evaluation

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


public class Critere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idCritere;
    @Column(nullable = false)
    String descriptionCritere;
    @Column(nullable = false)
    float noteMaxCritere;

    @OneToMany(mappedBy = "critere", cascade = CascadeType.ALL, orphanRemoval = true)
    List <SousCritere> sousCriteres;



}
