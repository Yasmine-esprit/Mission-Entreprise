package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.missionentreprise.Entities.GrilleEvaluation;
import tn.esprit.spring.missionentreprise.Entities.TypeGrilleEval;

import java.util.List;

public interface GrilleEvaluationRepository extends JpaRepository<GrilleEvaluation, Long> {
    List<GrilleEvaluation> findByTypeEval(TypeGrilleEval typeEval);}