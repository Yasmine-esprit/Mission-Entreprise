//Module Gestion User
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

public class GroupeMsg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idGrpMsg;

}
