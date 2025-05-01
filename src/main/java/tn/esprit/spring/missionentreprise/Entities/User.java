package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@FieldDefaults(level = AccessLevel.PRIVATE)


public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idUser ;

    String nomUser ;
    String prenomUser ;

    @Lob
    byte[] photoProfil;

    @OneToMany(mappedBy = "user")
    List <Post> posts;

    @OneToMany(mappedBy = "user")
    List <Interaction> interactions;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<GroupeMsg> groups;



}
