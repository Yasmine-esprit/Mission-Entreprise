package tn.esprit.spring.missionentreprise.Auth;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.missionentreprise.Entities.roleName;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder

public class RegistrationRequest {

    @NotEmpty(message = "Firstname is mandatory")
    @NotNull(message = "Firstname is mandatory")
    private String firstname;
    @NotEmpty(message = "Lastname is mandatory")
    @NotNull(message = "Lastname is mandatory")
    private String lastname;
    @Email(message = "Email is not well formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotNull(message = "Email is mandatory")
    private String email;
    @NotEmpty(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password;

    private MultipartFile photoProfil;

    private roleName role;



    // Etudiant
    private String matricule;
    private String niveau;
    private String specialite;
    private LocalDate dateNaissance;

    //enseignant
    private   String grade;
    private String demainRecherche; // Ex: L3, M1, M2
    private String Bureau;


    private String departement;
    private String anneeExperience;







}
