package tn.esprit.spring.missionentreprise;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.esprit.spring.missionentreprise.Entities.Role;
import tn.esprit.spring.missionentreprise.Entities.User;
import tn.esprit.spring.missionentreprise.Entities.roleName;
import tn.esprit.spring.missionentreprise.Repositories.RoleRepository;
import tn.esprit.spring.missionentreprise.Repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        // Vérifier si l'administrateur existe déjà
        if (userRepository.findByEmailUser("admin@example.com").isPresent()) {
            System.out.println("Admin already exists.");
            return;
        }

        // Vérifier ou créer le rôle ADMINISTRATEUR
        Role adminRole = roleRepository.findByRoleType(roleName.ADMINISTRATEUR)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleType(roleName.ADMINISTRATEUR);
                    return roleRepository.save(newRole);
                });

        // Créer et sauvegarder l'administrateur
        User admin = User.builder()
                .nomUser("Admin")
                .prenomUser("User")
                .emailUser("admin@example.com")
                .passwordUser(passwordEncoder.encode("admin123"))
                .enabledUser(true)
                .accountLockedUser(false)
                .roles(List.of(adminRole))
                .createdDate(LocalDateTime.now())
                .build();

        userRepository.save(admin);
        System.out.println("✅ Admin user created: admin@example.com / admin123");
    }
}
