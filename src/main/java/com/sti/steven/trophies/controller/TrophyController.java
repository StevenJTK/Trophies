package com.sti.steven.trophies.controller;


import com.sti.steven.trophies.game.Trophies;
import com.sti.steven.trophies.game.Trophy;
import com.sti.steven.trophies.service.TrophyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/games")
public class TrophyController {

    private final TrophyService trophyService;

    public TrophyController(TrophyService trophyService) {
        this.trophyService = trophyService;
    }

    // Refactor this by creating a DTO
    @GetMapping("/name/{gameName}/trophyName/{trophyName}")
    public Optional<Trophy> getTrophyName(@PathVariable String gameName, @PathVariable String trophyName) {
        return trophyService.findByGameNameAndTrophyName(gameName, trophyName);
    }
    // Refactor this by creating a DTO
 /*   @GetMapping("/name/{gameName}/trophies/{trophyDescription}")
    public Optional<Trophy> getTrophyDescription(@PathVariable String trophyDescription, @PathVariable String gameName) {
        return trophyService.findByTrophyDescription(trophyDescription, gameName);
    } */

  // This mapping does not work as of now - refactor later
   @GetMapping("/name/{gameName}/trophyType/{findByTrophyType}")
    public Optional<Trophy> getByTrophyType(@PathVariable Trophies findByTrophyType, @PathVariable String gameName) {
        return trophyService.findByTrophyType(gameName, findByTrophyType);
    }

    @GetMapping("/name/{gameName}/trophies")
    public List<Trophy> findAllByGameName(@PathVariable String gameName) {
        List<Trophy> trophies = trophyService.findAllByGame_GameName(gameName);
        System.out.println("Looking up trophies for game: [" + gameName + "]");
        return trophies;
    }
}
