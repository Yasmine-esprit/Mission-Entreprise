package tn.esprit.spring.missionentreprise.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "admin")
public class Admin extends User{

    private Date DateAjou;
    private Boolean superadmin;
}
