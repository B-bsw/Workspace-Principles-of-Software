package cp.com.lab076733800656sec2.service;

import cp.com.lab076733800656sec2.model.Game;
import cp.com.lab076733800656sec2.repository.GameRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> getAll() {
        return gameRepository.findAll();
    }
}
