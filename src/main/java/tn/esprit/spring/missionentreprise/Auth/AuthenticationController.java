package tn.esprit.spring.missionentreprise.Auth;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.missionentreprise.Entities.roleName;

import java.util.Map;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {



    private final AuthenticationService authenticationService;



    @PostMapping("/register")
    public ResponseEntity<String> register(@ModelAttribute RegistrationRequest request) throws MessagingException {
        System.out.println("mat " + request.getMatricule());
        return authenticationService.register(request);
    }

    @PutMapping("/enable/{email}")
    public ResponseEntity<String> enable(@PathVariable("email") String email) throws MessagingException {
        return authenticationService.enable(email);
    }


    @PostMapping("/forget")
    public ResponseEntity<?> forgetPassword( @RequestBody Map<String, String> request) throws MessagingException {

        return authenticationService.forgetPassword(request);

    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/verify-2fa")
    public ResponseEntity<?> verify2FA(
            @RequestBody TwoFactorRequest request
    ) {
        String email = request.getEmail();
        String code = request.getCode();
        return authenticationService.verify2FALogin(email, code);
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable String token ,@RequestBody Map<String, String> request) throws MessagingException {
        return authenticationService.resetPassword(token ,request);
    }
    @GetMapping("/activate-account")
    public void confirm(
            @RequestParam String token
    ) throws MessagingException {
        authenticationService.activateAccount(token);
    }



}
