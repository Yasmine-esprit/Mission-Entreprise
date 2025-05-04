package tn.esprit.spring.missionentreprise.Auth;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
import tn.esprit.spring.missionentreprise.Utils.TokenService;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private final TokenService tokenService;


    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public ResponseEntity<String> register(RegistrationRequest request) throws MessagingException {
        if (request.getFirstname() == null || request.getFirstname().trim().isEmpty() ||
                request.getLastname() == null || request.getLastname().trim().isEmpty() ||
                request.getEmail() == null || request.getEmail().trim().isEmpty() ||
                request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("All fields (firstname, lastname, email, password) are required.");
        }
        // Check if the user already exists
        if (userRepository.findByEmailUser(request.getEmail()).isPresent()) {
            return new ResponseEntity<>("User with email " + request.getEmail() + " already exists.", HttpStatus.BAD_REQUEST);
        }
        System.out.println(" role of user "+ request.getRole().toString());

        Optional<Role> optionalUserRole = roleRepository.findByRoleType(request.getRole());
        if (optionalUserRole.isEmpty()) {
            return new ResponseEntity<>("Role " + request.getRole() + " was not found.", HttpStatus.NOT_FOUND);
        }
        // Get the Role object from the Optional
        Role userRole = optionalUserRole.get();

        //convert multipart to byte[]
        byte[] photoBytes = null;
        if (request.getPhotoProfil() != null) {
            System.out.println("not null");
            try {
                photoBytes = request.getPhotoProfil().getBytes(); // Convert MultipartFile to byte[]
            } catch (IOException e) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error converting photo file: " + e.getMessage());
            }
        }
        var user = User.builder()
                .nomUser(request.getFirstname())
                .prenomUser(request.getLastname())
                .emailUser(request.getEmail())
                .passwordUser(passwordEncoder.encode(request.getPassword()))
                .accountLockedUser(false)
                .enabledUser(false)
                .roles(List.of(userRole))
                .createdDate(LocalDateTime.now())
                .photoProfil(photoBytes)
                .build();

            System.out.println(user.getFullName());
        System.out.println("Photo byte size = " + (photoBytes != null ? photoBytes.length : "null"));

        // Save the user
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while saving the user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Send email validation
        //sendValidationEmail(user);


        // If everything is successful, return 200 OK
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED); // 201 Created
    }


    public ResponseEntity<?> authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );


            User user = (User) authentication.getPrincipal();

            var claims = new HashMap<String, Object>();
            claims.put("fullName", user.getFullName());
            List<roleName> roleNames = user.getRoles().stream()
                    .map(Role::getRoleType) // assuming RoleType is a string or enum
                    .collect(Collectors.toList());

            System.out.println("User roles: " + roleNames);

            claims.put("roles", roleNames);



            String jwtToken = jwtService.generateToken(claims, user);

            AuthenticationResponse response = AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>("Invalid email or password.", HttpStatus.UNAUTHORIZED); // 401
        } catch (DisabledException ex) {
            return new ResponseEntity<>("Account is disabled. Please verify your email.", HttpStatus.FORBIDDEN); // 403
        } catch (Exception ex) {
            return new ResponseEntity<>("Authentication failed: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }


   // @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        System.out.println("saved " +savedToken);
            if (LocalDateTime.now().isAfter(savedToken.getExpirationDate())) {
           // sendValidationEmail(savedToken.getUser());
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
        System.out.println("newToken " + newToken);


            String subject = "Welcome to Our Platform!";
            String htmlBody = "<h1>Hello " + user.getFullName() + "</h1>"
                    + "<p>Thank you for registering. Your account is under review.</p>";

            emailService.sendEmail(user.getEmailUser(), subject, htmlBody);
        activateAccount(newToken);
        System.out.println("account activated");

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

    public ResponseEntity<?> forgetPassword(Map<String, String> request) throws MessagingException {
        System.out.println("forgetPassword " + request.get("email"));
        Optional<User> userOptional = userRepository.findByEmailUser( request.get("email"));

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User with email " +  request.get("email") + " n'existe pas.", HttpStatus.BAD_REQUEST);
        }


        User user = userOptional.get();

        Token token1 = tokenService.createPasswordResetToken(user);
        String resetLink = "http://localhost:4200/reset-password?token=" + token1.getToken();
        System.out.println("token " + token1.getToken());
        emailService.sendEmail(user.getEmailUser(),"Password Reset",
                "Click this link to reset your password: " + resetLink);

        return new ResponseEntity<>("Link sent", HttpStatus.OK);


    }


    public ResponseEntity<?> resetPassword(String token , Map<String, String> request) throws MessagingException {
        Optional<Token> token1 = tokenRepository.findByToken(token);
        String password = request.get("password");


        if (token1.isEmpty()) {
            return new ResponseEntity<>("token expired", HttpStatus.BAD_REQUEST);
        }
        User user = token1.get().getUser();
        System.out.println("user " + user.getFullName());
        System.out.println("old " + user.getPassword());
        System.out.println("password " + password);

        user.setPasswordUser(passwordEncoder.encode(password));
        System.out.println("new " + user.getPassword());

        userRepository.save(user);
        return new ResponseEntity<>("Mot de passe modifiee",HttpStatus.OK);

    }
}
