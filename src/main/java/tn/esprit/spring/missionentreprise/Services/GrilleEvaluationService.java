package tn.esprit.spring.missionentreprise.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.GrilleEvaluation;
import tn.esprit.spring.missionentreprise.Entities.TypeGrilleEval;
import tn.esprit.spring.missionentreprise.Repositories.GrilleEvaluationRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GrilleEvaluationService implements IServiceGenerique<GrilleEvaluation> {

    GrilleEvaluationRepository grilleEvaluationRepository;

    @Override
    public GrilleEvaluation add(GrilleEvaluation grilleEvaluation) {
        if (grilleEvaluation.getCriteres() == null || grilleEvaluation.getCriteres().isEmpty()) {
            throw new IllegalArgumentException("Grille must contain at least one critere");
        }
        return grilleEvaluationRepository.save(grilleEvaluation);
    }

    @Override
    public List<GrilleEvaluation> addAll(List<GrilleEvaluation> grilleEvaluations) {
        grilleEvaluations.forEach(grille -> {
            if (grille.getCriteres() == null || grille.getCriteres().isEmpty()) {
                throw new IllegalArgumentException("All grilles must contain at least one critere");
            }
        });
        return grilleEvaluationRepository.saveAll(grilleEvaluations);
    }

    @Override
    public List<GrilleEvaluation> getAll() {
        return grilleEvaluationRepository.findAll();
    }

    @Override
    public GrilleEvaluation edit(GrilleEvaluation grilleEvaluation) {
        if (grilleEvaluation.getCriteres() == null || grilleEvaluation.getCriteres().isEmpty()) {
            throw new IllegalArgumentException("Grille must contain at least one critere");
        }
        return grilleEvaluationRepository.save(grilleEvaluation);
    }

    @Override
    public List<GrilleEvaluation> editAll(List<GrilleEvaluation> grilleEvaluations) {
        grilleEvaluations.forEach(grille -> {
            if (grille.getCriteres() == null || grille.getCriteres().isEmpty()) {
                throw new IllegalArgumentException("All grilles must contain at least one critere");
            }
        });
        return grilleEvaluationRepository.saveAll(grilleEvaluations);
    }

    @Override
    public void delete(GrilleEvaluation grilleEvaluation) {
        grilleEvaluationRepository.delete(grilleEvaluation);
    }

    @Override
    public void deleteAll() {
        grilleEvaluationRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        grilleEvaluationRepository.deleteById(id);
    }

    @Override
    public GrilleEvaluation getById(Long id) {
        return grilleEvaluationRepository.findById(id)
                .orElse(GrilleEvaluation.builder()
                        .idEvaluation(0L)
                        .nomEvaluation("NullEvaluation")
                        .build());
    }

    @Override
    public boolean existsById(Long id) {
        return grilleEvaluationRepository.existsById(id);
    }

    @Override
    public Long count() {
        return grilleEvaluationRepository.count();
    }


    public List<GrilleEvaluation> findByTypeEval(TypeGrilleEval typeEval) {
        return grilleEvaluationRepository.findByTypeEval(typeEval);
    }
}