package tn.esprit.spring.missionentreprise;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tn.esprit.spring.missionentreprise.Entities.Role;
import tn.esprit.spring.missionentreprise.Entities.roleName;
import tn.esprit.spring.missionentreprise.Repositories.RoleRepository;

import java.time.LocalDateTime;
@Component
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public void run(String... args) throws Exception {
        // Loop over all the role types in the roleName enum
        for (roleName roleType : roleName.values()) {
            // Check if the role already exists in the database
            roleRepository.findByRoleType(roleType).ifPresentOrElse(
                    role -> {
                        // Role exists, do nothing
                        System.out.println("Role " + roleType + " already exists.");
                    },
                    () -> {
                        // Role does not exist, create and save it with proper dates
                        Role newRole = new Role();
                        newRole.setRoleType(roleType);
                        roleRepository.save(newRole);  // Save to the database
                        System.out.println("Role " + roleType + " created.");
                    }
            );
        }

    }
}
