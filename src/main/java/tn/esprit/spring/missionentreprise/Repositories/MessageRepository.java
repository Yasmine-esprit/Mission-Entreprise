package tn.esprit.spring.missionentreprise.Repositories;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.spring.missionentreprise.Entities.GroupeMsg;
import tn.esprit.spring.missionentreprise.Entities.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<List<Message>> findByGroupeMsg(GroupeMsg groupeMsg);

    @NotNull Optional<Message> findById(@NotNull Long id);


}
