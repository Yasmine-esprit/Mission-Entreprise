package tn.esprit.spring.missionentreprise.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Like;
import tn.esprit.spring.missionentreprise.Entities.User;
import tn.esprit.spring.missionentreprise.Entities.Interaction;
import tn.esprit.spring.missionentreprise.Repositories.LikeRepository;

@Service
@AllArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    public Like save(Like like) {
        return likeRepository.save(like);
    }

    public boolean hasUserLiked(User user, Interaction interaction) {
        return likeRepository.existsByUserAndInteraction(user, interaction);
    }
} 