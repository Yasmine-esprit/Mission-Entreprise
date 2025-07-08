package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.missionentreprise.Entities.Classe;

import java.util.List;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
    List<Classe> findByDepartement_IdDepartement(Long departementId);
    List<Classe> findByNiveau_IdNiveau(Long niveauId);
}
