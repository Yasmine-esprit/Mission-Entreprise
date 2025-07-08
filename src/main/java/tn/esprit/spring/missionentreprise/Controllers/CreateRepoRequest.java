package tn.esprit.spring.missionentreprise.Controllers;

import lombok.Data;

@Data
public class CreateRepoRequest {
    private String repoName;
    private String description;
    private boolean isPrivate;
}
