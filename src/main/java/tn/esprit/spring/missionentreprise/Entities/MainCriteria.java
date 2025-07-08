// Module Gestion Evaluation

package tn.esprit.spring.missionentreprise.Entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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




public class MainCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idMainCritere;
    @Column(nullable = false)
    String descMainCritere;
    @Column(nullable = false)
    Float MaxPoints;
    @ManyToOne
    Critere critere;

    @OneToMany(mappedBy = "mainCritere", cascade = CascadeType.ALL, orphanRemoval = true)
    List <SousCritere> sousCriteres = new ArrayList<>();



}
