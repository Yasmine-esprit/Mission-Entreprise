package tn.esprit.spring.missionentreprise.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.missionentreprise.Entities.User;
import tn.esprit.spring.missionentreprise.Services.UserService;
import tn.esprit.spring.missionentreprise.Utils.NullPropertyUtils;
import tn.esprit.spring.missionentreprise.Utils.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /* ------------------------------------------------------------------
     *                      CRUD & LISTES
     * ------------------------------------------------------------------ */

    @GetMapping("list")
    public ResponseEntity<?> getUsers() {
        try {
            List<UserDTO> users = userService.getAllUsersDTO();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /** Liste filtrée : uniquement les étudiants */
    @GetMapping("list/etudiants")
    public List<UserDTO> getAllEtudiants() {
        return userService.getAllEtudiants()
                          .stream()
                          .map(this::convertToDTO)
                          .collect(Collectors.toList());
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        try {
            User user = userService.getById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        try {
            return userService.getCurrentUser();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("by-group/{groupId}")
    public ResponseEntity<?> getUsersByGroupId(@PathVariable Long groupId) {
        try {
            return userService.getUsersByGroupId(groupId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteById(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserDTO updatedUser) {
        User user = userService.getById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        String[] nullProps = NullPropertyUtils.getNullPropertyNames(updatedUser);
        BeanUtils.copyProperties(updatedUser, user, nullProps);
        userService.edit(user);

        return ResponseEntity.ok("User updated successfully");
    }

    /* ------------------------------------------------------------------
     *                      Upload / Download photo
     * ------------------------------------------------------------------ */

    @PostMapping("/{id}/upload-photo")
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            userService.uploadPhoto(id, file);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<?> getPhoto(@PathVariable Long id) {
        try {
            return userService.getPhoto(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* ------------------------------------------------------------------
     *                 Recherche d’étudiants (YassminT)
     * ------------------------------------------------------------------ */

    @GetMapping("/EtudiantByName")
    public List<UserDTO> searchEtudiantsByName(@RequestParam String searchTerm) {
        String searchLower = searchTerm.toLowerCase();
        return userService.searchEtudiantsByName(searchLower)
                          .stream()
                          .filter(user -> user.getRoles().contains("ETUDIANT"))
                          .filter(User::isEnabledUser)
                          .filter(user -> !user.isAccountLockedUser())
                          .map(this::convertToDTO)
                          .collect(Collectors.toList());
    }

    /* ------------------------------------------------------------------ */
    /*                      Helper DTO converter                          */
    /* ------------------------------------------------------------------ */

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                      .idUser(user.getIdUser())
                      .nomUser(user.getNomUser())
                      .prenomUser(user.getPrenomUser())
                      .emailUser(user.getEmailUser())
                      .enabledUser(user.isEnabledUser())
                      .accountLockedUser(user.isAccountLockedUser())
                      .roles(user.getRoles().stream().map(Object::toString).collect(Collectors.toList()))
                      .photoProfil(user.getPhotoProfil())
                      .build();
    }
}
