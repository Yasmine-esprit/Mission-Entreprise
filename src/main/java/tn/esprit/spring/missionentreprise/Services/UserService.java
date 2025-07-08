package tn.esprit.spring.missionentreprise.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.missionentreprise.Entities.User;
import tn.esprit.spring.missionentreprise.Entities.roleName;
import tn.esprit.spring.missionentreprise.Repositories.UserRepository;
import tn.esprit.spring.missionentreprise.Utils.TokenRepository;
import tn.esprit.spring.missionentreprise.Utils.UserDTO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IServiceGenerique<User> {


    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }


    @Override
    public List<User> addAll(List<User> t) {
        return List.of();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User edit(User user) {

        return userRepository.save(user);
    }

    @Override
    public List<User> editAll(List<User> t) {
        return List.of();
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteAll() {

    }
    @Transactional
    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        tokenRepository.deleteByUser(user);
        userRepository.deleteById(id);

    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public Long count() {
        return userRepository.count();
    }

    public List<UserDTO> getAllUsersDTO() {
        return userRepository.findAll().stream().map(user -> {
            List<String> roleNames = user.getRoles().stream()
                    .map(role -> role.getRoleType().name())
                    .collect(Collectors.toList());
            return new UserDTO(
                    user.getIdUser(),
                    user.getNomUser(),
                    user.getPrenomUser(),
                    user.getEmailUser(),
                    user.isEnabledUser(),
                    user.isAccountLockedUser(),
                    roleNames,
                    user.getPhotoProfil()
            );
        }).collect(Collectors.toList());
    }

    public ResponseEntity<?> getCurrentUser() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> user = userRepository.findByEmailUser(email);

            if (user.isEmpty()) {
                return new ResponseEntity<>("User NOT FOUND", HttpStatus.NOT_FOUND);
            }


            UserDTO userDTO = UserDTO.builder()
                    .idUser(user.get().getIdUser())
                    .nomUser(user.get().getNomUser())
                    .prenomUser(user.get().getPrenomUser())
                    .emailUser(user.get().getEmailUser())
                    .enabledUser(user.get().isEnabledUser())
                    .accountLockedUser(user.get().isAccountLockedUser())
                    .roles(user.get().getRoles().stream().map(role -> role.getRoleType().name()).collect(Collectors.toList()))
                    .photoProfil(user.get().getPhotoProfil())
                    .build();

            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<List<User>> getUsersByGroupId(Long groupId) {
        List<User> userList = userRepository.findUsersByGroupId(groupId);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    public ResponseEntity<?> uploadPhoto(Long id, MultipartFile file) {
        return userRepository.findById(id).map(user -> {
            try {
                user.setPhotoProfil(file.getBytes());
                userRepository.save(user);
                return ResponseEntity.ok("Photo uploaded successfully.");
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading image.");
            }
        }).orElse(ResponseEntity.notFound().build());
    }
    public ResponseEntity<?> getPhoto(Long id) {
        Optional<User> user = userRepository.findById(id);
        System.out.println(user.isEmpty());
        if (user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User NOT FOUND");

        }
        else {
            byte[] photo = user.get().getPhotoProfil();
            System.out.println(photo.length);
            return ResponseEntity.ok(photo);


        }


    }

    //ajoutés par YassminT pour récupération des etudiants
    public List<User> getAllEtudiants() {
        return userRepository.findByRoleTypeAndEnabledAndNotLocked(roleName.ETUDIANT);
    }

    public List<User> searchEtudiantsByName(String searchTerm) {
        return userRepository.findEtudiantsByNameOrEmailContaining(searchTerm);
    }

}
