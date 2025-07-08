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
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idDepartement;
    
    String nomDepartement;
    String description;
    
    @ManyToOne
    @JoinColumn(name = "institution_id")
    Institution institution;
    
    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL)
    List<Classe> classes;
}
