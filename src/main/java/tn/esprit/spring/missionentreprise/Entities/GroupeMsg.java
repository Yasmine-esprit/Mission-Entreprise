//Module Gestion User
package tn.esprit.spring.missionentreprise.Entities;


<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
>>>>>>> ceadf4d (test)
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

<<<<<<< HEAD
=======
import java.util.HashSet;
import java.util.Set;
>>>>>>> ceadf4d (test)


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
<<<<<<< HEAD

=======
// ignore both the users set and the messages set
@JsonIgnoreProperties({ "users", "messages" })
>>>>>>> ceadf4d (test)
public class GroupeMsg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idGrpMsg;

<<<<<<< HEAD
=======
    @Lob
    byte[] imageGroupe;

    @Enumerated(EnumType.STRING)
    GroupAssociation nbrePersonne;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy="groupeMsg", fetch = FetchType.LAZY)
    private Set<Message> messages;



>>>>>>> ceadf4d (test)
}
