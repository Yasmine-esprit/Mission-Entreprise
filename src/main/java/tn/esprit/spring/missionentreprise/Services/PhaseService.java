package tn.esprit.spring.missionentreprise.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Phase;
import tn.esprit.spring.missionentreprise.Repositories.PhaseRepository;

import java.util.List;

@Service
public class PhaseService {

    @Autowired
    private PhaseRepository phaseRepository;

    public Phase ajouterPhase(Phase phase) {
        return phaseRepository.save(phase);
    }

    public List<Phase> getAllPhases() {
        return phaseRepository.findAll();
    }

    public Phase getPhaseById(Long id) {
        return phaseRepository.findById(id).orElse(null);
    }

    public Phase modifierPhase(Long id, Phase updatedPhase) {
        Phase phase = phaseRepository.findById(id).orElse(null);
        if (phase != null) {
            phase.setTitrePhase(updatedPhase.getTitrePhase());
            phase.setDescription(updatedPhase.getDescription());
            phase.setDateDebut(updatedPhase.getDateDebut());
            phase.setDateFin(updatedPhase.getDateFin());
            phase.setStatut(updatedPhase.getStatut());
            phase.setModule(updatedPhase.getModule());
            return phaseRepository.save(phase);
        }
        return null;
    }

    public void supprimerPhase(Long id) {
        phaseRepository.deleteById(id);
    }
}
