//Module Gestion Depot

package tn.esprit.spring.missionentreprise.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

public class Interaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInteraction;

    private LocalDate dateInteraction;
    private Integer typeInteraction;

    @Column(length = 500)
    private String contenuInteraction;

    @ManyToOne
    @JoinColumn(name = "post_id_post")
    @JsonIgnore
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id_user")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "interaction")
    private List<Like> likes;

    // getters and setters

    public String getContenuInteraction() {
        return contenuInteraction;
    }
    public void setContenuInteraction(String contenuInteraction) {
        this.contenuInteraction = contenuInteraction;
    }
}
