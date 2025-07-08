package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.Interaction;
import tn.esprit.spring.missionentreprise.Entities.Like;
import tn.esprit.spring.missionentreprise.Entities.User;
import tn.esprit.spring.missionentreprise.Entities.Post;
import tn.esprit.spring.missionentreprise.Repositories.UserRepository;
import tn.esprit.spring.missionentreprise.Repositories.PostRepository;
import tn.esprit.spring.missionentreprise.Repositories.InteractionRepository;
import tn.esprit.spring.missionentreprise.Services.InteractionService;
import tn.esprit.spring.missionentreprise.Services.LikeService;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/interactions")
@AllArgsConstructor
public class InteractionController {
    private final InteractionService interactionService;
    private final LikeService likeService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final InteractionRepository interactionRepository;

    @PostMapping
    public ResponseEntity<?> createInteraction(@RequestBody InteractionRequest request) {
        Interaction interaction = new Interaction();
        interaction.setDateInteraction(request.getDateInteraction());
        interaction.setTypeInteraction(request.getTypeInteraction());
        interaction.setContenuInteraction(request.getContenuInteraction());
        postRepository.findById(request.getPostId()).ifPresent(interaction::setPost);
        userRepository.findById(request.getUserId()).ifPresent(interaction::setUser);
        Interaction saved = interactionService.save(interaction);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{interactionId}/like")
    public ResponseEntity<?> likeInteraction(@PathVariable Long interactionId, @RequestBody LikeRequest request) {
        var interactionOpt = interactionRepository.findById(interactionId);
        var userOpt = userRepository.findById(request.getUserId());
        if (interactionOpt.isEmpty() || userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Interaction or user not found");
        }
        if (likeService.hasUserLiked(userOpt.get(), interactionOpt.get())) {
            return ResponseEntity.badRequest().body("User already liked this interaction");
        }
        Like like = new Like();
        like.setInteraction(interactionOpt.get());
        like.setUser(userOpt.get());
        likeService.save(like);
        return ResponseEntity.ok("Liked!");
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getAllInteractionsByPost(@PathVariable Long postId) {
        var dtos = interactionService.findByPostId(postId).stream()
            .map(interaction -> {
                InteractionDTO dto = new InteractionDTO();
                dto.setIdInteraction(interaction.getIdInteraction());
                dto.setContenuInteraction(interaction.getContenuInteraction());
                dto.setDateInteraction(interaction.getDateInteraction());
                dto.setAuthorName(interaction.getUser() != null ? interaction.getUser().getNomUser() : null);
                dto.setPostId(interaction.getPost() != null ? interaction.getPost().getIdPost() : null);
                return dto;
            })
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // DTOs
    public static class InteractionRequest {
        private LocalDate dateInteraction;
        private Integer typeInteraction;
        private Long postId;
        private Long userId;
        private String contenuInteraction;
        public LocalDate getDateInteraction() { return dateInteraction; }
        public void setDateInteraction(LocalDate dateInteraction) { this.dateInteraction = dateInteraction; }
        public Integer getTypeInteraction() { return typeInteraction; }
        public void setTypeInteraction(Integer typeInteraction) { this.typeInteraction = typeInteraction; }
        public Long getPostId() { return postId; }
        public void setPostId(Long postId) { this.postId = postId; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getContenuInteraction() { return contenuInteraction; }
        public void setContenuInteraction(String contenuInteraction) { this.contenuInteraction = contenuInteraction; }
    }
    public static class LikeRequest {
        private Long userId;
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
    }

    // DTO for listing interactions
    public static class InteractionDTO {
        private Long idInteraction;
        private String contenuInteraction;
        private java.time.LocalDate dateInteraction;
        private String authorName;
        private Long postId;
        public Long getIdInteraction() { return idInteraction; }
        public void setIdInteraction(Long idInteraction) { this.idInteraction = idInteraction; }
        public String getContenuInteraction() { return contenuInteraction; }
        public void setContenuInteraction(String contenuInteraction) { this.contenuInteraction = contenuInteraction; }
        public java.time.LocalDate getDateInteraction() { return dateInteraction; }
        public void setDateInteraction(java.time.LocalDate dateInteraction) { this.dateInteraction = dateInteraction; }
        public String getAuthorName() { return authorName; }
        public void setAuthorName(String authorName) { this.authorName = authorName; }
        public Long getPostId() { return postId; }
        public void setPostId(Long postId) { this.postId = postId; }
    }
} 