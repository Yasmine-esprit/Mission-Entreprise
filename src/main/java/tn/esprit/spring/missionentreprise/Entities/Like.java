package tn.esprit.spring.missionentreprise.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "like_entity", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id_user", "interaction_id_interaction"}))
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLike;

    @ManyToOne
    @JoinColumn(name = "interaction_id_interaction")
    @JsonIgnore
    private Interaction interaction;

    @ManyToOne
    @JoinColumn(name = "user_id_user")
    private User user;

    // getters and setters

    public Long getIdLike() {
        return idLike;
    }

    public void setIdLike(Long idLike) {
        this.idLike = idLike;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
} 