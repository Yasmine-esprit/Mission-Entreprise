package tn.esprit.spring.missionentreprise;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tn.esprit.spring.missionentreprise.Auth.AuthenticationResponse;
import tn.esprit.spring.missionentreprise.Entities.Admin;
import tn.esprit.spring.missionentreprise.Entities.Role;
import tn.esprit.spring.missionentreprise.Entities.User;
import tn.esprit.spring.missionentreprise.Entities.roleName;
import tn.esprit.spring.missionentreprise.Repositories.RoleRepository;
import tn.esprit.spring.missionentreprise.Repositories.UserRepository;
import tn.esprit.spring.missionentreprise.Security.JwtService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class etudiantInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override

    public void run(String... args) throws Exception {

        if (userRepository.findByEmailUser("yassmin.tlemsani@esprit.tn").isPresent()) {
            return; // Admin already created
        }

        // Check if ADMIN role exists, else create it
        Role etudiantRole = roleRepository.findByRoleType(roleName.ETUDIANT)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleType(roleName.ETUDIANT);
                    return roleRepository.save(newRole);
                });

        // Create and save admin user
        User etudiant = User.builder()
                .nomUser("Yassmin")
                .prenomUser("Tlemsani")
                .emailUser("yassmin.tlemsani@esprit.tn")
                .passwordUser(passwordEncoder.encode("yassmin123"))
                .enabledUser(false)
                .accountLockedUser(false)
                .roles(List.of(etudiantRole))
                .createdDate(LocalDateTime.now())
                .build();

        userRepository.save(etudiant);
        System.out.println("Etudiant user created: yassmin.tlemsani@esprit.tn / yassmin123");
    }



}

