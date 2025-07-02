package tn.esprit.spring.missionentreprise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tn.esprit.spring.missionentreprise.Entities.*;
import tn.esprit.spring.missionentreprise.Repositories.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@Component
public class CompteTestInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private InstitutionRepository institutionRepository;
    
    @Autowired
    private DepartementRepository departementRepository;
    
    @Autowired
    private ClasseRepository classeRepository;
    
    @Autowired
    private GroupeRepository groupeRepository;
    
    @Autowired
    private NiveauRepository niveauRepository;
    
    @Autowired
    private ModuleRepository moduleRepository;
    
    @Autowired
    private ThemeRepository themeRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Initialisation des comptes de test...");
        
        createRoles();
        createTestAccounts();
        createOrganisationHierarchy();
        
        System.out.println("✅ Comptes de test créés avec succès !");
    }
    
    private void createRoles() {
        for (roleName roleType : roleName.values()) {
            Optional<Role> existingRole = roleRepository.findByRoleType(roleType);
            if (existingRole.isEmpty()) {
                Role newRole = Role.builder()
                        .roleType(roleType)
                        .build();
                roleRepository.save(newRole);
                System.out.println("✅ Rôle créé : " + roleType);
            }
        }
    }
    
    private void createTestAccounts() {
        // Admin
        createUserIfNotExists("admin@unigit.tn", "admin123", "Admin", "System", roleName.ADMINISTRATEUR);
        
        // Enseignant
        createUserIfNotExists("enseignant@esprit.tn", "enseignant123", "Mohamed", "Bennour", roleName.ENSEIGNANT);
        
        // Coordinateur
        createUserIfNotExists("coordinateur@esprit.tn", "coordinateur123", "Fatma", "Karoui", roleName.COORDINATEUR);
        
        // Étudiant
        createUserIfNotExists("etudiant@esprit.tn", "etudiant123", "Ahmed", "Trabelsi", roleName.ETUDIANT);
    }
    
    private void createUserIfNotExists(String email, String password, String nom, String prenom, roleName roleType) {
        if (userRepository.findByEmailUser(email).isEmpty()) {
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
                                .secret(null) // Désactiver 2FA pour les tests
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
                                .secret(null) // Désactiver 2FA pour les tests
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
                                .secret(null) // Désactiver 2FA pour les tests
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
                                .secret(null) // Désactiver 2FA pour les tests
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
                    System.out.println("✅ Utilisateur créé : " + email + " (" + roleType + ")");
                }
            }
        }
    }
    
    private void createOrganisationHierarchy() {
        if (institutionRepository.count() == 0) {
            // Créer institution
            Institution esprit = Institution.builder()
                    .nomInstitution("ESPRIT")
                    .adresse("Ariana, Tunisie")
                    .description("École Supérieure Privée d'Ingénierie et de Technologies")
                    .build();
            institutionRepository.save(esprit);
            
            // Créer départements
            Departement deptInfo = Departement.builder()
                    .nomDepartement("Informatique et Management")
                    .description("Département des technologies informatiques")
                    .institution(esprit)
                    .build();
            departementRepository.save(deptInfo);
            
            Departement deptTelecom = Departement.builder()
                    .nomDepartement("Télécommunications")
                    .description("Département des réseaux et télécommunications")
                    .institution(esprit)
                    .build();
            departementRepository.save(deptTelecom);
            
            // Créer niveaux
            Niveau licence3 = Niveau.builder()
                    .nomNiveau("Licence 3")
                    .build();
            niveauRepository.save(licence3);
            
            // Créer classes
            Classe classe3InfoA = Classe.builder()
                    .nomClasse("3INFO-A")
                    .departement(deptInfo)
                    .niveau(licence3)
                    .build();
            classeRepository.save(classe3InfoA);
            
            Classe classe3InfoB = Classe.builder()
                    .nomClasse("3INFO-B")
                    .departement(deptInfo)
                    .niveau(licence3)
                    .build();
            classeRepository.save(classe3InfoB);
            
            // Créer groupes
            Groupe groupe1A = Groupe.builder()
                    .nomGroupe("Groupe 1")
                    .classe(classe3InfoA)
                    .visibilite(Visibilite.PUBLIC)
                    .build();
            groupeRepository.save(groupe1A);
            
            Groupe groupe2A = Groupe.builder()
                    .nomGroupe("Groupe 2")
                    .classe(classe3InfoA)
                    .visibilite(Visibilite.PUBLIC)
                    .build();
            groupeRepository.save(groupe2A);
            
            // Créer modules
            tn.esprit.spring.missionentreprise.Entities.Module moduleWeb = tn.esprit.spring.missionentreprise.Entities.Module.builder()
                    .nomModule("Développement Web")
                    .niveau(licence3)
                    .build();
            moduleRepository.save(moduleWeb);
            
            // Créer thèmes
            Theme themeEcommerce = Theme.builder()
                    .titreTheme("Plateforme E-commerce")
                    .description("Développement d'une plateforme de commerce électronique complète")
                    .module(moduleWeb)
                    .classe(classe3InfoA)
                    .build();
            themeRepository.save(themeEcommerce);
            
            Theme themeReseauSocial = Theme.builder()
                    .titreTheme("Réseau Social")
                    .description("Création d'un réseau social pour étudiants")
                    .module(moduleWeb)
                    .classe(classe3InfoA)
                    .build();
            themeRepository.save(themeReseauSocial);
            
            Theme themeAppMobile = Theme.builder()
                    .titreTheme("Application Mobile de Gestion")
                    .description("Application mobile pour la gestion de projets")
                    .module(moduleWeb)
                    .groupe(groupe1A)
                    .build();
            themeRepository.save(themeAppMobile);
            
            System.out.println("✅ Hiérarchie organisationnelle créée");
        }
    }
}
