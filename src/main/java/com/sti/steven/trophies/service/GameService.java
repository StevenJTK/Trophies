package com.sti.steven.trophies.service;


import com.sti.steven.trophies.game.Game;
import com.sti.steven.trophies.interfaces.GameRepository;
import com.sti.steven.trophies.interfaces.TrophyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final TrophyRepository trophyRepository;

    @Autowired
    public GameService(GameRepository gameRepository, TrophyRepository trophyRepository) {
        this.gameRepository = gameRepository;
        this.trophyRepository = trophyRepository;
    }

    public Game findByGameName(String gameName) {
        Game game;
        if(gameName == null) {
            throw new IllegalArgumentException("Game must have a name");
        }

        game = gameRepository.findByGameName(gameName);

        if(game == null) {
            throw new IllegalArgumentException("Game not found");
        }
        return game;
    }

    public Game findByGameDescription(String description) {
        Game game;
        if(description == null) {
            throw new IllegalArgumentException("Game must have a description");
        }
        game = gameRepository.findByGameDescription(description);

        if(game == null) {
            throw new IllegalArgumentException("Game was not found");
        }
        return game;
    }

    public Game findByGenre(String genre) {
        Game game;
        if(genre == null) {
            throw new IllegalArgumentException("Game must have a genre");
        }
        game = gameRepository.findByGenre(genre);
        if(game == null) {
            throw new IllegalArgumentException("Game was not found");
        }
        return game;
    }

    public Game findByReleaseDate(String releaseDate) {
        Game game;
        if(releaseDate == null) {
            throw new IllegalArgumentException("Release date must have a date");
        }
        game = gameRepository.findByReleaseDate(releaseDate);
        if(game == null) {
            throw new IllegalArgumentException("Game date was not found");
        }
        return game;
    }

    public Game findByDeveloper(String developer) {
        Game game;
        if(developer == null) {
            throw new IllegalArgumentException("Developer must exist");
        }

        game = gameRepository.findByDeveloper(developer);
        if(game == null) {
            throw new IllegalArgumentException("Studio was not found");
        }
        return game;
    }


}
