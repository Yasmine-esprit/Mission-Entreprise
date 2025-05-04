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

    public ResponseEntity<String> register(
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            @RequestParam(value = "photoProfil", required = false) MultipartFile photo) throws MessagingException {
        roleName roleN = roleName.valueOf(role);
        var request = RegistrationRequest.builder().firstname(firstname).lastname(lastname)
                .email(email).password(password).role(roleN).photoProfil(photo).build();

        System.out.println("in contorller" + request.getPhotoProfil().getName());
        return authenticationService.register(request);

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
