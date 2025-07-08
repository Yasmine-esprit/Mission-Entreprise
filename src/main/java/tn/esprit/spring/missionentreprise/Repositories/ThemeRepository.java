package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.missionentreprise.Entities.Theme;

import java.util.List;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    List<Theme> findByClasse_IdCLasse(Long classeId);
    List<Theme> findByGroupe_IdGroupe(Long groupeId);
}
