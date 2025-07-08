package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Services.GitRepositoryService;
import lombok.Data;
import java.util.List;
import tn.esprit.spring.missionentreprise.Repositories.EtudiantRepository;
import tn.esprit.spring.missionentreprise.Repositories.GroupeRepository;
import tn.esprit.spring.missionentreprise.Repositories.RepoRepository;
import tn.esprit.spring.missionentreprise.Entities.Etudiant;
import tn.esprit.spring.missionentreprise.Entities.Groupe;
import tn.esprit.spring.missionentreprise.Entities.Repo;
import org.springframework.security.core.context.SecurityContextHolder;
import tn.esprit.spring.missionentreprise.Repositories.UserRepository;

@Data
class CloneBody {
    String url;
    String name;
}

@RestController
@RequestMapping("/git")
@AllArgsConstructor
public class GitTestController {

    private final GitRepositoryService gitRepositoryService;
    private final EtudiantRepository etudiantRepository;
    private final GroupeRepository groupeRepository;
    private final RepoRepository repoRepository;
    private final UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createRepo(@RequestBody CreateRepoRequest request) {
        try {
            String result = gitRepositoryService.createGitHubRepository(
                request.getRepoName(), 
                request.getDescription(), 
                request.isPrivate()
            );
            // Get current username (email) from SecurityContext
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            // Find User by username (email)
            tn.esprit.spring.missionentreprise.Entities.User user = userRepository.findByEmailUser(username).orElseThrow(() -> new RuntimeException("User not found"));
            Long userId = user.getIdUser();
            // Find Etudiant by id_user
            Etudiant etudiant = etudiantRepository.findById(userId).orElse(null);
            if (etudiant == null || etudiant.getGroupe() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User or group not found");
            }
            // Create and save Repo entity
            Repo repo = Repo.builder()
                .nomRepo(request.getRepoName())
                .groupe(etudiant.getGroupe())
                .sousTache(null)
                .build();
            repoRepository.save(repo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    // Endpoint to clone a repository
    @PostMapping("/clone")
    public ResponseEntity<?> cloneRepo(@RequestBody CloneBody body) {
        try {
            System.out.print("test");
            String result = gitRepositoryService.cloneRepository(body.getUrl(), body.getName());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace(); // Add this for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/commit")
    public ResponseEntity<?> commitAndPush(@RequestBody CommitRequest request) {
        try {
            String result = gitRepositoryService.commitAndPushChanges(request.getRepoName(), request.getMessage());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/branches/{repoName}")
    public ResponseEntity<?> listBranches(@PathVariable String repoName) {
        try {
            List<String> branches = gitRepositoryService.listBranches(repoName);
            return ResponseEntity.ok(branches);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error listing branches: " + e.getMessage());
        }
    }

    @GetMapping("/local-repos")
    public ResponseEntity<?> getLocalRepositories() {
        try {
            List<GitRepositoryService.RepositoryInfo> repos = gitRepositoryService.listLocalRepositories();
            return ResponseEntity.ok(repos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching local repositories: " + e.getMessage());
        }
    }

}
