package tn.esprit.spring.missionentreprise.Auth;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor

public class AuthenticationController {



    private final AuthenticationService authenticationService;



    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED) ///@Valid : has problem to check it later
    public ResponseEntity<?> register(
            @RequestBody  RegistrationRequest request
    ) throws MessagingException {
        System.out.println("registrationRequest " + request);
        System.out.println(request.getFirstname() + " " + request.getLastname());
        return authenticationService.register(request);

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @GetMapping("/activate-account")
    public void confirm(
            @RequestParam String token
    ) throws MessagingException {
        authenticationService.activateAccount(token);
    }



}
