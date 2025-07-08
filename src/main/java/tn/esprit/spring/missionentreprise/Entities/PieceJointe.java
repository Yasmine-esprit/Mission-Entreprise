package tn.esprit.spring.missionentreprise.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "piece_jointe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PieceJointe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPieceJointe;

    @Column
    String nom;

    @Column(nullable = false)
    String url;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    TypePieceJointe type = TypePieceJointe.FICHIER;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_ajout", updatable = false)
    @Builder.Default
    Date dateAjout = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tache_id", nullable = false)
    @JsonIgnore //je teste avec
    Tache tache;

    @PrePersist
    protected void onCreate() {
        dateAjout = new Date();
    }
}