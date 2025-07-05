package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.missionentreprise.Entities.Tache;

public interface TacheRepository extends JpaRepository<Tache, Long> {
}
