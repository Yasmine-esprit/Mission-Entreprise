//Module Gestion User
package tn.esprit.spring.missionentreprise.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
// ignore both the users set and the messages set
@JsonIgnoreProperties({ "users", "messages" })
public class GroupeMsg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idGrpMsg;

    @Lob
    byte[] imageGroupe;

    @Enumerated(EnumType.STRING)
    GroupAssociation nbrePersonne;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy="groupeMsg", fetch = FetchType.LAZY ,  orphanRemoval = true)
    private Set<Message> messages;



}
