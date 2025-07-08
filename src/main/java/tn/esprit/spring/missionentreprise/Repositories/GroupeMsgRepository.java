package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.missionentreprise.Entities.GroupeMsg;
import tn.esprit.spring.missionentreprise.Entities.User;

import java.util.List;
import java.util.Optional;

public interface GroupeMsgRepository extends JpaRepository<GroupeMsg, Long> {

    List<GroupeMsg> findByUsersContaining(User user);

    Optional<GroupeMsg> findByIdGrpMsg(Long id);
}
