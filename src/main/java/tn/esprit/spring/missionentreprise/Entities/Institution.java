//Module Gestion Organisation

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
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idInstitution;
    
    String nomInstitution;
    String adresse;
    String description;
    
    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    List<Departement> departements;
}
