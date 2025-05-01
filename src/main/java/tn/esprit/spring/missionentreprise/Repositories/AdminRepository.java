package tn.esprit.spring.missionentreprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.missionentreprise.Entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
