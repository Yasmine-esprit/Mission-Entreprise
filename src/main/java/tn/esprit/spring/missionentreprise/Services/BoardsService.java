package tn.esprit.spring.missionentreprise.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Boards;
import tn.esprit.spring.missionentreprise.Repositories.BoardsRepository;

import java.util.Optional;

@Service
public class BoardsService {

    @Autowired
    private BoardsRepository boardRepository;

    public Boards addBoard(Boards board) {
        return boardRepository.save(board);
    }

    public boolean deleteBoardById(Long id) {
        if (boardRepository.existsById(id)) {
            boardRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteBoardByName(String name) {
        Optional<Boards> boardOptional = boardRepository.findByName(name);
        if (boardOptional.isPresent()) {
            boardRepository.delete(boardOptional.get());
            return true;
        }
        return false;
    }

    public Optional<Boards> getBoardById(Long id) {
        return boardRepository.findById(id);
    }
}
