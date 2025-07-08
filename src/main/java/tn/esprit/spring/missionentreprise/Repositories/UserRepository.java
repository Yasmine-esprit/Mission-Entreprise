package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.spring.missionentreprise.Entities.Role;
import tn.esprit.spring.missionentreprise.Entities.User;
import tn.esprit.spring.missionentreprise.Entities.roleName;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailUser(String email);

    @Query("SELECT u FROM User u JOIN u.groups g WHERE g.idGrpMsg = :groupId")
    List<User> findUsersByGroupId(@Param("groupId") Long groupId);

    //added by YassminT
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleType = :roleName AND u.enabledUser = true AND u.accountLockedUser = false")
    List<User> findByRoleTypeAndEnabledAndNotLocked(@Param("roleName") roleName roleName);

    @Query("SELECT u FROM User u WHERE " +
            "(LOWER(u.nomUser) LIKE %:searchTerm% OR " +
            "LOWER(u.prenomUser) LIKE %:searchTerm% OR " +
            "LOWER(u.emailUser) LIKE %:searchTerm%) AND " +
            "u.enabledUser = true AND " +
            "u.accountLockedUser = false")
    List<User> findEtudiantsByNameOrEmailContaining(@Param("searchTerm") String searchTerm);
}
