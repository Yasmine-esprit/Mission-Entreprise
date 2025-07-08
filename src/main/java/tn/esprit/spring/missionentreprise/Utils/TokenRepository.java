package tn.esprit.spring.missionentreprise.Utils;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.missionentreprise.Entities.User;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByToken(String token);
    void deleteByToken(String token);
    void deleteByUser(User user);
}
