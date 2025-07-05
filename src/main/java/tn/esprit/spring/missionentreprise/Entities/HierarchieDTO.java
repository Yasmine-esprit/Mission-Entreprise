package tn.esprit.spring.missionentreprise.Entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HierarchieDTO {
    List<InstitutionDTO> institutions;
}

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
class InstitutionDTO {
    Long idInstitution;
    String nomInstitution;
    String adresse;
    String description;
    List<DepartementDTO> departements;
}

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
class DepartementDTO {
    Long idDepartement;
    String nomDepartement;
    String description;
    List<ClasseDTO> classes;
}

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
class ClasseDTO {
    Long idClasse;
    String nomClasse;
    List<GroupeDTO> groupes;
}

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
class GroupeDTO {
    Long idGroupe;
    String nomGroupe;
    String visibilite;
}
