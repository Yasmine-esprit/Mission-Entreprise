package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.missionentreprise.Entities.SousCritere;

import java.util.List;

public interface SousCritereRepository extends JpaRepository<SousCritere, Long> {
    // Find by name containing (case insensitive)
    List<SousCritere> findByNameSousCritereContainingIgnoreCase(String name);

    // Find by main criteria
    List<SousCritere> findByMainCritere_IdMainCritere(Long mainCriteriaId);
}
