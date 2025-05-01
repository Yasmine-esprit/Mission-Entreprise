//Module Gestion Depot

package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPost;
    String titrePost;
    String contenuPost;
    LocalDate datePost;

    @ManyToOne
    User user;

    @OneToMany(mappedBy = "post")
    List<Interaction> interactions;
}

