package com.sti.steven.trophies.controller;

import com.sti.steven.trophies.game.Game;
import com.sti.steven.trophies.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/name/{gameName}")
    public Game getGameByName(@PathVariable String gameName) {
        return gameService.findByGameName(gameName);
    }


    @GetMapping("/genre/{genre}")
    public Game getGameByGenre(@PathVariable String genre) {
        return gameService.findByGenre(genre);
    }

    @GetMapping("/developer/{developer}")
    public Game getGameByDeveloper(@PathVariable String developer) {
        return gameService.findByDeveloper(developer);
    }

    @GetMapping("/description")
    public Game getGameByDescription(String description) {
        return gameService.findByGameDescription(description);
    }

    @GetMapping("/release")
    public Game getGameByRelease(String release) {
        return gameService.findByReleaseDate(release);
    }
}
