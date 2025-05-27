package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.spring.missionentreprise.Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailUser(String email);

    @Query("SELECT u FROM User u JOIN u.groups g WHERE g.idGrpMsg = :groupId")
    List<User> findUsersByGroupId(@Param("groupId") Long groupId);

}
