package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


import java.util.List;


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
    //Byte [] photoProfil;   //it generated an error, maybe consider changing the type of the attribute

    @OneToMany(mappedBy = "user")
    List <Post> posts;

    @OneToMany(mappedBy = "user")
    List <Interaction> interactions;
}
