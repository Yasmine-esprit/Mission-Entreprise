package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "theme")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idTheme;

    String titreTheme;

    String description;

    @ManyToOne
    @JoinColumn(name = "module_id")
    Module module;
    
    @ManyToOne
    @JoinColumn(name = "classe_id")
    Classe classe;

    @ManyToOne
    @JoinColumn(name = "groupe_id")
    Groupe groupe;


}
