package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Boards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBoard;

    private String name;
    private String color;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Tache> taches;


}

