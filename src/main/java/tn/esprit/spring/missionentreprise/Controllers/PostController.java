package tn.esprit.spring.missionentreprise.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.missionentreprise.Entities.Post;
import tn.esprit.spring.missionentreprise.Entities.User;
import tn.esprit.spring.missionentreprise.Repositories.UserRepository;
import tn.esprit.spring.missionentreprise.Services.PostService;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
        if (postRequest.getTitrePost() == null || postRequest.getTitrePost().trim().isEmpty() ||
            postRequest.getContenuPost() == null || postRequest.getContenuPost().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("titrePost and contenuPost are required");
        }
        Post post = new Post();
        post.setIdPost(postRequest.getId());
        post.setContenuPost(postRequest.getContenuPost());
        post.setDatePost(postRequest.getDatePost());
        post.setTitrePost(postRequest.getTitrePost());
        if (postRequest.getUserId() != null) {
            userRepository.findById(postRequest.getUserId()).ifPresent(post::setUser);
        } else {
            post.setUser(null);
        }
        Post saved = postService.save(post);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        var dtos = postService.findAll().stream()
            .map(post -> {
                PostDTO dto = new PostDTO();
                dto.setIdPost(post.getIdPost());
                dto.setTitrePost(post.getTitrePost());
                dto.setContenuPost(post.getContenuPost());
                dto.setDatePost(post.getDatePost());
                dto.setAuthorName(post.getUser() != null ? post.getUser().getNomUser() : null);
                return dto;
            })
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        var postOpt = postService.findById(id);
        if (postOpt.isPresent()) {
            var post = postOpt.get();
            PostDTO dto = new PostDTO();
            dto.setIdPost(post.getIdPost());
            dto.setTitrePost(post.getTitrePost());
            dto.setContenuPost(post.getContenuPost());
            dto.setDatePost(post.getDatePost());
            dto.setAuthorName(post.getUser() != null ? post.getUser().getNomUser() : null);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(404).body("Post not found");
        }
    }

    // DTO for post creation
    public static class PostRequest {
        private Long id;
        private String contenuPost;
        private LocalDate datePost;
        private String titrePost;
        private Long userId;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getContenuPost() { return contenuPost; }
        public void setContenuPost(String contenuPost) { this.contenuPost = contenuPost; }
        public LocalDate getDatePost() { return datePost; }
        public void setDatePost(LocalDate datePost) { this.datePost = datePost; }
        public String getTitrePost() { return titrePost; }
        public void setTitrePost(String titrePost) { this.titrePost = titrePost; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
    }

    // DTO for listing posts
    public static class PostDTO {
        private Long idPost;
        private String titrePost;
        private String contenuPost;
        private java.time.LocalDate datePost;
        private String authorName;
        public Long getIdPost() { return idPost; }
        public void setIdPost(Long idPost) { this.idPost = idPost; }
        public String getTitrePost() { return titrePost; }
        public void setTitrePost(String titrePost) { this.titrePost = titrePost; }
        public String getContenuPost() { return contenuPost; }
        public void setContenuPost(String contenuPost) { this.contenuPost = contenuPost; }
        public java.time.LocalDate getDatePost() { return datePost; }
        public void setDatePost(java.time.LocalDate datePost) { this.datePost = datePost; }
        public String getAuthorName() { return authorName; }
        public void setAuthorName(String authorName) { this.authorName = authorName; }
    }
} 