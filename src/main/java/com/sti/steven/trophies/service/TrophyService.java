package com.sti.steven.trophies.service;

import com.sti.steven.trophies.game.Trophies;
import com.sti.steven.trophies.game.Trophy;
import com.sti.steven.trophies.interfaces.GameRepository;
import com.sti.steven.trophies.interfaces.TrophyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrophyService {

    private final TrophyRepository trophyRepository;


    @Autowired
    public TrophyService(TrophyRepository trophyRepository) {
        this.trophyRepository = trophyRepository;
    }

    public Optional<Trophy> findByTrophyType(String gameName, Trophies type) {
        return trophyRepository.findByGame_GameNameAndTrophyType(gameName, type);
    }

    public Optional<Trophy> findByTrophyDescription(String trophyDescription, String gameName) {
        return trophyRepository.findByGame_GameNameAndTrophyDescription(gameName, trophyDescription);
    }
    public Optional<Trophy> findByGameNameAndTrophyName(String gameName, String trophyName) {
        return trophyRepository.findByGame_GameNameAndTrophyName(gameName, trophyName);
    }
}
