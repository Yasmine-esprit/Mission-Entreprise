package tn.esprit.spring.missionentreprise.Auth;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private String token;
    private boolean mfaEnabled;
}
