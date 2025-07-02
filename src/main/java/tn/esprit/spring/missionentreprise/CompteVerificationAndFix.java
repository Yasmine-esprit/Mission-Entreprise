package tn.esprit.spring.missionentreprise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tn.esprit.spring.missionentreprise.Entities.*;
import tn.esprit.spring.missionentreprise.Repositories.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Order(2) // S'exécute après les rôles
public class CompteVerificationAndFix implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🔍 Vérification et correction des comptes...");
        
        fixOrCreateAccounts();
        
        System.out.println("✅ Comptes vérifiés et corrigés !");
    }
    
    private void fixOrCreateAccounts() {
        // Liste des comptes à créer/corriger
        String[][] accounts = {
            {"admin@unigit.tn", "admin123", "Admin", "System", "ADMINISTRATEUR"},
            {"enseignant@esprit.tn", "enseignant123", "Mohamed", "Bennour", "ENSEIGNANT"},
            {"coordinateur@esprit.tn", "coordinateur123", "Fatma", "Karoui", "COORDINATEUR"},
            {"etudiant@esprit.tn", "etudiant123", "Ahmed", "Trabelsi", "ETUDIANT"}
        };
        
        for (String[] account : accounts) {
            String email = account[0];
            String password = account[1];
            String nom = account[2];
            String prenom = account[3];
            String roleStr = account[4];
            
            System.out.println("🔧 Traitement du compte : " + email);
            
            // Chercher l'utilisateur existant
            Optional<User> existingUser = userRepository.findByEmailUser(email);
            
            if (existingUser.isPresent()) {
                // Utilisateur existe, le corriger
                User user = existingUser.get();
                
                // Corriger le mot de passe
                user.setPasswordUser(passwordEncoder.encode(password));
                
                // FORCER secret à null pour désactiver 2FA
                user.setSecret(null);
                
                // S'assurer qu'il est activé
                user.setEnabledUser(true);
                user.setAccountLockedUser(false);
                
                userRepository.save(user);
                System.out.println("✅ Compte corrigé : " + email + " (mot de passe + 2FA désactivé)");
                
            } else {
                // Créer le compte s'il n'existe pas
                createNewAccount(email, password, nom, prenom, roleName.valueOf(roleStr));
            }
        }
    }
    
    private void createNewAccount(String email, String password, String nom, String prenom, roleName roleType) {
        Optional<Role> role = roleRepository.findByRoleType(roleType);
        if (role.isPresent()) {
            User user = null;
            
            switch (roleType) {
                case ADMINISTRATEUR:
                    user = Admin.builder()
                            .nomUser(nom)
                            .prenomUser(prenom)
                            .emailUser(email)
                            .passwordUser(passwordEncoder.encode(password))
                            .enabledUser(true)
                            .accountLockedUser(false)
                            .secret(null) // FORCÉMENT null
                            .roles(Arrays.asList(role.get()))
                            .build();
                    break;
                    
                case ENSEIGNANT:
                    user = Enseignant.builder()
                            .nomUser(nom)
                            .prenomUser(prenom)
                            .emailUser(email)
                            .passwordUser(passwordEncoder.encode(password))
                            .enabledUser(true)
                            .accountLockedUser(false)
                            .secret(null) // FORCÉMENT null
                            .roles(Arrays.asList(role.get()))
                            .grade("Professeur")
                            .specialite("Informatique")
                            .demainRecherche("Développement Web")
                            .Bureau("B101")
                            .build();
                    break;
                    
                case COORDINATEUR:
                    user = Coordinateur.builder()
                            .nomUser(nom)
                            .prenomUser(prenom)
                            .emailUser(email)
                            .passwordUser(passwordEncoder.encode(password))
                            .enabledUser(true)
                            .accountLockedUser(false)
                            .secret(null) // FORCÉMENT null
                            .roles(Arrays.asList(role.get()))
                            .build();
                    break;
                    
                case ETUDIANT:
                    user = Etudiant.builder()
                            .nomUser(nom)
                            .prenomUser(prenom)
                            .emailUser(email)
                            .passwordUser(passwordEncoder.encode(password))
                            .enabledUser(true)
                            .accountLockedUser(false)
                            .secret(null) // FORCÉMENT null
                            .roles(Arrays.asList(role.get()))
                            .matricule("E20230001")
                            .niveau("L3")
                            .specialite("Informatique")
                            .dateNaissance(LocalDate.of(2000, 1, 1))
                            .choixEffectue(false)
                            .build();
                    break;
            }
            
            if (user != null) {
                userRepository.save(user);
                System.out.println("✅ Nouveau compte créé : " + email + " (" + roleType + ")");
            }
        }
    }
}
