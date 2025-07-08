package tn.esprit.spring.missionentreprise.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Interaction;
import tn.esprit.spring.missionentreprise.Repositories.InteractionRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class InteractionService {
    private final InteractionRepository interactionRepository;

    public Interaction save(Interaction interaction) {
        return interactionRepository.save(interaction);
    }

    public List<Interaction> findByPostId(Long postId) {
        return interactionRepository.findByPost_IdPost(postId);
    }
} 