package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.missionentreprise.Entities.PieceJointe;
import tn.esprit.spring.missionentreprise.Entities.Priorite;
import tn.esprit.spring.missionentreprise.Entities.Statut;
import tn.esprit.spring.missionentreprise.Entities.Tache;

import java.util.List;

@Repository
public interface TacheRepository extends JpaRepository<Tache, Long> {

    List<Tache> findByStatut(Statut statut);

    List<Tache> findByPriorite(Priorite priorite);

    List<Tache> findByAssigneA(String assigneA);

    List<Tache> findByProjetIdProjet(Long idProjet);
    List<Tache> findByBoardIdBoard(Long idBoard);
    List<Tache> findByPhaseIdPhase(Long idPhase);

    @Query("SELECT t FROM Tache t WHERE t.idTache = :id")
    List<Tache> findByIdWithDates(@Param("id") Long id);

}
