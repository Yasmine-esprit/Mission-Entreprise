package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.missionentreprise.Entities.Critere;

import java.util.List;

public interface CritereRepository extends JpaRepository<Critere, Long> {
 }
