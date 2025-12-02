package com.sti.steven.trophies.controller;


import com.sti.steven.trophies.game.Trophy;
import com.sti.steven.trophies.service.TrophyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/games")
public class TrophyController {

    private final TrophyService trophyService;

    public TrophyController(TrophyService trophyService) {
        this.trophyService = trophyService;
    }

    @GetMapping("{gameName}/trophies/{trophyName}")
    public Optional<Trophy> getTrophyName(@PathVariable String gameName, @PathVariable String trophyName) {
        return trophyService.findByGameNameAndTrophyName(gameName, trophyName);
    }

    @GetMapping("/trophies/{trophyDescription}")
    public Trophy getTrophyDescription(@PathVariable String trophyDescription) {
        return trophyService.findByTrophyDescription(trophyDescription);
    }
}
