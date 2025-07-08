package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.missionentreprise.Entities.Repo;
import tn.esprit.spring.missionentreprise.Entities.Groupe;
import java.util.List;

public interface RepoRepository extends JpaRepository<Repo, Long> {
    List<Repo> findByGroupe(Groupe groupe);
} 