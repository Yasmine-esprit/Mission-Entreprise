//Module Gestion User

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

@Table(name = "ensignant")
public class Enseignant extends User{
    String grade;
    String demainRecherche; // Ex: L3, M1, M2
    String Bureau; // Info, GTR, etc.

    @OneToMany(mappedBy = "enseignant")
    List <Projet> projets;

    @OneToMany(mappedBy = "enseignant")
    List <NoteTGrp> noteTGrps;

    @OneToMany(mappedBy = "enseignant")
    List <NoteTIndiv> noteTIndiv;

}
