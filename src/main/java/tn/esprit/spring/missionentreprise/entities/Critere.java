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


public class Critere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCritere;
    @Column(nullable = false)
    private String descriptionCritere;
    @Column(nullable = false)
    private float noteMaxCritere;
    StatusValidation statusValidation;



}
