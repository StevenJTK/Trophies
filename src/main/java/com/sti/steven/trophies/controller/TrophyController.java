package com.sti.steven.trophies.controller;


import com.sti.steven.trophies.game.Trophy;
import com.sti.steven.trophies.service.TrophyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games/{gameName}")
public class TrophyController {

    private final TrophyService trophyService;

    public TrophyController(TrophyService trophyService) {
        this.trophyService = trophyService;
    }

    @GetMapping("/trophies/{trophyName}")
    public Trophy getTrophyName(@PathVariable String trophyName) {
        return trophyService.findByTrophyName(trophyName);
    }

    @GetMapping("/trophies/{trophyDescription}")
    public Trophy getTrophyDescription(@PathVariable String trophyDescription) {
        return trophyService.findByTrophyDescription(trophyDescription);
    }
}
