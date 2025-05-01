//Module Gestion User
package tn.esprit.spring.missionentreprise.Entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


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
    @Lob
    byte[] imageGroupe;

    GroupAssociation nbrePersonne;

    @ManyToMany(mappedBy = "groups")
    private Set<User> users;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="groupeMsg")
    private Set<Message> messages;



}
