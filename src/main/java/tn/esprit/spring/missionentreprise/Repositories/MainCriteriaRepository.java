package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.missionentreprise.Entities.MainCriteria;

import java.util.List;

public interface MainCriteriaRepository extends JpaRepository<MainCriteria, Long> {

    // Find main criteria by description containing (case insensitive)
    List<MainCriteria> findByDescMainCritereContainingIgnoreCase(String description);
}
