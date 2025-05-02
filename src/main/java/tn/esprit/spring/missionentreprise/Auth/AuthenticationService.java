package tn.esprit.spring.missionentreprise.Auth;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.missionentreprise.Entities.Role;
import tn.esprit.spring.missionentreprise.Entities.User;
import tn.esprit.spring.missionentreprise.Entities.roleName;
import tn.esprit.spring.missionentreprise.Repositories.RoleRepository;
import tn.esprit.spring.missionentreprise.Repositories.UserRepository;
import tn.esprit.spring.missionentreprise.Security.JwtService;
import tn.esprit.spring.missionentreprise.Utils.EmailService;
import tn.esprit.spring.missionentreprise.Utils.Token;
import tn.esprit.spring.missionentreprise.Utils.TokenRepository;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager; // à ignorer
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;



    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public ResponseEntity<String> register(RegistrationRequest request) {
        // Check if the user already exists
        if (userRepository.findByEmailUser(request.getEmail()).isPresent()) {
            return new ResponseEntity<>("User with email " + request.getEmail() + " already exists.", HttpStatus.BAD_REQUEST);
        }


        Optional<Role> optionalUserRole = roleRepository.findByRoleType(roleName.ETUDIANT);
        if (optionalUserRole.isEmpty()) {
            return new ResponseEntity<>("Role " + roleName.ETUDIANT + " was not found.", HttpStatus.NOT_FOUND);
        }
        // Get the Role object from the Optional
        Role userRole = optionalUserRole.get();

        var user = User.builder()
                .nomUser(request.getFirstname())
                .prenomUser(request.getLastname())
                .emailUser(request.getEmail())
                .passwordUser(passwordEncoder.encode(request.getPassword()))
                .accountLockedUser(false)
                .enabledUser(false)
                .roles(List.of(userRole))
                .createdDate(LocalDateTime.now())
                .photoProfil(request.getPhotoProfil())
                .build();

        System.out.println("user before snedinf "+ user);
        // Save the user
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while saving the user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Send email validation
        try {
            sendValidationEmail(user);
        } catch (MessagingException e) {
            return new ResponseEntity<>("Failed to send validation email: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // If everything is successful, return 200 OK
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED); // 201 Created
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        System.out.println("auth " + auth );
        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.getFullName());

        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        System.out.println("saved " +savedToken);
            if (LocalDateTime.now().isAfter(savedToken.getExpirationDate())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been send to the same email address");
        }

        var user = userRepository.findById(savedToken.getUser().getIdUser())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabledUser(true);
        userRepository.save(user);

        savedToken.setValidatedDate(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    private String generateAndSaveActivationToken(User user) {
        // Generate a token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .creationDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmailUser(),
                user.getFullName(),
                //EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }
}
