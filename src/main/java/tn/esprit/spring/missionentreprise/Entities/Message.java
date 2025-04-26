package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "message")
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idm;

    private String content;

    private Date dateEnvoi;

    private boolean lu;



}
