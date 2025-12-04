package com.sti.steven.trophies.controller;

import com.sti.steven.trophies.game.Game;
import com.sti.steven.trophies.service.GameService;
import com.sti.steven.trophies.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;
    private final UserService userService;

    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
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

    @PostMapping("/users/{userId}/trophies/{trophyId}/complete")
    public ResponseEntity<String> completeTrophy(@PathVariable Integer userId,
                                                 @PathVariable Integer trophyId) {
        userService.completeTrophyForUser(userId, trophyId);
        return ResponseEntity.ok("Congratulations! Trophy unlocked.");
    }
}
