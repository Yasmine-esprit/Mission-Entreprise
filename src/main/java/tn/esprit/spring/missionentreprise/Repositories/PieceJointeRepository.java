package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.missionentreprise.Entities.PieceJointe;

import java.util.List;

public interface PieceJointeRepository extends JpaRepository<PieceJointe, Long> {
    List<PieceJointe> findByTache_IdTache(Long idTache);
}
