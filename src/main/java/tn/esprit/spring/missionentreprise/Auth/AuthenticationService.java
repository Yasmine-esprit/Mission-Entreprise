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
import tn.esprit.spring.missionentreprise.Entities.*;
import tn.esprit.spring.missionentreprise.Repositories.EtudiantRepository;
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
    private final EtudiantRepository etudiantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager; // à ignorer
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;
    private final TwoFactorAuthenticationService twoFactorAuthenticationService;


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
                .enabledUser(false) // Initially, the user is not enabled for 2FA
                .roles(List.of(userRole))
                .createdDate(LocalDateTime.now())
                .photoProfil(photoBytes)
                .build();

            System.out.println(user.getFullName());
        System.out.println("Photo byte size = " + (photoBytes != null ? photoBytes.length : "null"));

        var jwtToken = jwtService.generateToken(user);
        AuthenticationResponse.builder()
                .mfaEnabled(user.isEnabledUser())
                .token(jwtToken).build();
        // Generate 2FA secret
        String secret = twoFactorAuthenticationService.generateSecret();

        // Create the QR code URL for the user to scan with Google Authenticator
        String qrCodeUrl = twoFactorAuthenticationService.generateQRCode(user.getEmailUser(), secret);



        // Save the 2FA secret in the database
        user.setSecret(secret);
        // Save the user
        try {
            // Vérifier si l'utilisateur est un étudiant
            if ("ETUDIANT".equalsIgnoreCase(request.getRole().toString())) {
                Etudiant etudiant = new Etudiant();
                etudiant.setNomUser(request.getFirstname());
                etudiant.setPrenomUser(request.getLastname());
                etudiant.setEmailUser(request.getEmail());
                etudiant.setPasswordUser(passwordEncoder.encode(request.getPassword()));
                etudiant.setAccountLockedUser(false);
                etudiant.setEnabledUser(false);
                etudiant.setCreatedDate(LocalDateTime.now());
                etudiant.setPhotoProfil(photoBytes);
                etudiant.setSecret(secret);
                etudiant.setRoles(List.of(userRole));
                System.out.println("mat : "+ request.getMatricule());
                // Champs spécifiques à Etudiant
                etudiant.setMatricule(request.getMatricule());
                etudiant.setNiveau(request.getNiveau());
                etudiant.setSpecialite(request.getSpecialite());
                etudiant.setDateNaissance(request.getDateNaissance());

                System.out.println("etudiant " + etudiant.getFullName() + "matricule " +
                        etudiant.getMatricule());
                userRepository.save(etudiant);
            } else if ("ENSEIGNANT".equalsIgnoreCase(request.getRole().toString())) {
                Enseignant enseignant = Enseignant.builder()
                        .nomUser(request.getFirstname())
                        .prenomUser(request.getLastname())
                        .emailUser(request.getEmail())
                        .passwordUser(passwordEncoder.encode(request.getPassword()))
                        .accountLockedUser(false)
                        .enabledUser(false) // Initially, the user is not enabled for 2FA
                        .roles(List.of(userRole))
                        .createdDate(LocalDateTime.now())
                        .photoProfil(photoBytes)
                        .secret(secret)
                        .roles(List.of(userRole))
                        .grade(request.getGrade())
                        .demainRecherche(request.getDemainRecherche())
                        .Bureau(request.getBureau())
                        .build();
                System.out.println("enseignant " + enseignant.getFullName());
                userRepository.save(enseignant);
            }
            else{
                Coordinateur coordinateur = Coordinateur.builder()
                        .nomUser(request.getFirstname())
                        .prenomUser(request.getLastname())
                        .emailUser(request.getEmail())
                        .passwordUser(passwordEncoder.encode(request.getPassword()))
                        .accountLockedUser(false)
                        .enabledUser(false) // Initially, the user is not enabled for 2FA
                        .roles(List.of(userRole))
                        .createdDate(LocalDateTime.now())
                        .photoProfil(photoBytes)
                        .secret(secret)
                        .roles(List.of(userRole))
                        .departement(request.getDepartement())
                        .anneeExperience(request.getAnneeExperience())
                        .build();
                System.out.println("coordinateur " + coordinateur.getFullName());
                userRepository.save(coordinateur);
            }


            // Envoi du mail avec le lien
            emailService.sendTwoFactorSetupEmail(user.getEmailUser(), user.getFullName(), qrCodeUrl);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while saving the user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
//
//

        // If everything is successful, return 200 OK
        return new ResponseEntity<>("User registered successfully. Please scan the QR code with your authenticator app: " ,HttpStatus.CREATED);
    }


    public  ResponseEntity<String> enable (String email){
        Optional<User> userOptional = userRepository.findByEmailUser(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();
        user.setEnabledUser(true);
        userRepository.save(user);
        return ResponseEntity.ok("User enabled successfully");
    }



    public ResponseEntity<?> authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            System.out.println(" manager " + authentication.isAuthenticated());


            User user = (User) authentication.getPrincipal();

            // Step 2: Check if 2FA is enabled for the user
            if (user.isEnabledUser() &&
                    user.getRoles().stream()
                            .noneMatch(role -> role.getRoleType().toString().equals("ADMINISTRATEUR"))) {
                Map<String, String> responseMessage = new HashMap<>();
                responseMessage.put("message", "Please enter the 2FA code.");
                return ResponseEntity.ok(responseMessage);
            }


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
                    .mfaEnabled(user.isEnabledUser())
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
    public ResponseEntity<?> verify2FALogin( String email, String code) {
        User user = userRepository.findByEmailUser(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Check if 2FA is enabled
        if (!user.isEnabledUser()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("2FA is not enabled for this user.");
        }

        // Validate the entered TOTP code using the stored secret
        boolean isValid = twoFactorAuthenticationService.validateCode(user.getSecret(), Integer.parseInt(code));
        if (isValid) {
            // Issue JWT or session, granting access
            var claims = new HashMap<String, Object>();
            claims.put("fullName", user.getFullName());
            List<roleName> roleNames = user.getRoles().stream()
                    .map(Role::getRoleType)
                    .collect(Collectors.toList());

            claims.put("roles", roleNames);

            String jwtToken = jwtService.generateToken(claims, user);

            AuthenticationResponse response = AuthenticationResponse.builder()
                    .token(jwtToken)
                    .mfaEnabled(user.isEnabledUser())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid 2FA code.", HttpStatus.UNAUTHORIZED);
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
