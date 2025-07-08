package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.missionentreprise.Entities.Departement;

import java.util.List;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {
    List<Departement> findByInstitution_IdInstitution(Long institutionId);
}
