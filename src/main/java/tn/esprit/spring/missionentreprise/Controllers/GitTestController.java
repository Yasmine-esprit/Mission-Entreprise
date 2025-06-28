package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Services.GitRepositoryService;
import lombok.Data;
import java.util.List;

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

    @PostMapping("/create")
    public ResponseEntity<?> createRepo(@RequestBody CreateRepoRequest request) {
        try {
            String result = gitRepositoryService.createGitHubRepository(request.getRepoName());
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
            List<String> repos = gitRepositoryService.listLocalRepositories();
            return ResponseEntity.ok(repos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching local repositories: " + e.getMessage());
        }
    }

}
