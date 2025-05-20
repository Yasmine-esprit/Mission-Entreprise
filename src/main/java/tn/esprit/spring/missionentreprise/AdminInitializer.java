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
public class AdminInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override

    public void run(String... args) throws Exception {

        if (userRepository.findByEmailUser("admin@example.com").isPresent()) {
            return; // Admin already created
        }

        // Check if ADMIN role exists, else create it
        Role adminRole = roleRepository.findByRoleType(roleName.ADMINISTRATEUR)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleType(roleName.ADMINISTRATEUR);
                    return roleRepository.save(newRole);
                });

        // Create and save admin user
        User admin = User.builder()
                .nomUser("Admin")
                .prenomUser("User")
                .emailUser("admin@example.com")
                .passwordUser(passwordEncoder.encode("admin123"))
                .enabledUser(true) // no 2FA required
                .accountLockedUser(false)
                .roles(List.of(adminRole))
                .createdDate(LocalDateTime.now())
                .build();

        userRepository.save(admin);
        System.out.println("Admin user created: admin@example.com / admin123");
    }
        

}

