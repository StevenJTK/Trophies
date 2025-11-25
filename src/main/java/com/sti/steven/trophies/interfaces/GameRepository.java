package com.sti.steven.trophies.interfaces;

import com.sti.steven.trophies.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {
    Game findByGameName(String gameName);
    Game findByGameDescription(String gameDescription);
    Game findByGenre(String genre);
    Game findByReleaseDate(String releaseDate);
    Game findByDeveloper(String developer);
}
