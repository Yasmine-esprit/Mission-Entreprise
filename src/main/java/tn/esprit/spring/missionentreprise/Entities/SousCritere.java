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
    String nameSousCritere;
    @Column(nullable = false)
    Long maxPoints;
    @Enumerated(EnumType.STRING)
    GradingLevels gradingLevels;
    @Enumerated(EnumType.ORDINAL)
    PoinRangesSubCrit poinRangesSubCrit;
    @Enumerated(EnumType.STRING)
    DescSubCriteria descSubCriteria;
    Long noteMax;

    @ManyToOne
    MainCriteria mainCritere;



}
