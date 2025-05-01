// Module Gestion Evaluation

package tn.esprit.spring.missionentreprise.Entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;



@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)


public class SousCritere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idSousCritere;
    @Column(nullable = false)
    String descriptionSousCritere;
    Long noteMax;
    Long noteMin;

    @ManyToOne
    Critere critere;



}
