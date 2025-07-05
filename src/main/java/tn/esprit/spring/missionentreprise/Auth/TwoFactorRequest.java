package tn.esprit.spring.missionentreprise.Auth;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder

public class TwoFactorRequest {
    private String email;
    private String code;
}
