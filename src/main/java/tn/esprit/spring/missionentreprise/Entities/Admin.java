//Module Gestion User

package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


import java.time.LocalDate;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder


@FieldDefaults(level = AccessLevel.PRIVATE)


public class Admin extends User{

   LocalDate DateAjou;
   Boolean superadmin;
}
