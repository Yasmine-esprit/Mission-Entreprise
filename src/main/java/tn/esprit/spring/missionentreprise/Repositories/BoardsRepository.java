package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.missionentreprise.Entities.Boards;

import java.util.Optional;

public interface BoardsRepository extends JpaRepository<Boards, Long >{
    Optional<Boards> findByName(String name);
}
