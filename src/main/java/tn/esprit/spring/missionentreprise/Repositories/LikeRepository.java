package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.missionentreprise.Entities.Like;
import tn.esprit.spring.missionentreprise.Entities.User;
import tn.esprit.spring.missionentreprise.Entities.Interaction;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndInteraction(User user, Interaction interaction);
} 