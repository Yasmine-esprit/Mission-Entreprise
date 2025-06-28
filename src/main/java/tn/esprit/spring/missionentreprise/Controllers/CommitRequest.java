package tn.esprit.spring.missionentreprise.Controllers;

import lombok.Data;

@Data
class CommitRequest {
    private String repoName;
    private String message;
}