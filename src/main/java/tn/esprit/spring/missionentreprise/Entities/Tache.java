package tn.esprit.spring.missionentreprise.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Getter
@Setter
@ToString(exclude = {"piecesJointes", "checklist", "sousTaches"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTache;

    @NotNull
    @NotBlank(message = "Le titre de la tâche ne peut pas être vide")
    @Column(nullable = false)
    String titreTache;

    @NotNull
    @NotBlank(message = "La description de la tâche ne peut pas être vide")
    @Column(nullable = false, length = 1000)
    String descriptionTache;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date_debut")
    LocalDate dateDebut;

    @Column(name = "date_fin")
    LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    Statut statut;

    @Enumerated(EnumType.STRING)
    Priorite priorite;

    @Column(name = "assigne_a")
    String assigneA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_id")
    Etudiant etudiant;

    @ElementCollection
    @CollectionTable(name = "tache_labels", joinColumns = @JoinColumn(name = "tache_id"))
    @Column(name = "label")
    @Builder.Default
    List<String> labels = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "tache_members", joinColumns = @JoinColumn(name = "tache_id"))
    @Column(name = "member")
    @Builder.Default
    List<String> members = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "tache", fetch = FetchType.LAZY)
    @Builder.Default
    List<PieceJointe> piecesJointes = new ArrayList<>();

    @Column(name = "cover_color")
    String coverColor;

    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference //pour éviter loop dans la sérialization
    private List<ChecklistItem> checklist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projet_id")
    private Projet projet;

    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @JsonManagedReference
    List<SousTache> sousTaches = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boards_id")
    private Boards board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phase_id")
    Phase phase;

    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    Date lastUpdated;

    @Column(name = "last_synced")
    @Temporal(TemporalType.TIMESTAMP)
    Date lastSynced;

    @Column(name = "pending_changes")
    Boolean pendingChanges;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.lastUpdated = new Date();
        this.pendingChanges = true;
    }

    public void markAsSynced() {
        this.lastSynced = new Date();
        this.pendingChanges = false;
    }

}