package cp.com.lab076733800656sec2.repository;

import cp.com.lab076733800656sec2.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {}
