package service;

import game.Trophy;
import interfaces.GameRepository;
import interfaces.TrophyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrophyService {

    private final TrophyRepository trophyRepository;
    private final GameRepository gameRepository;

    @Autowired
    public TrophyService(TrophyRepository trophyRepository, GameRepository gameRepository) {
        this.trophyRepository = trophyRepository;
        this.gameRepository = gameRepository;
    }

    public Trophy findByTrophyName(String name) {
        Trophy trophy;

        if(name == null) {
            throw new IllegalArgumentException("Trophy name cannot be null");
        }

        trophy = trophyRepository.findByTrophyName(name);
        if(trophy == null) {
            throw new IllegalArgumentException("Trophy not found");
        }

        return trophy;
    }

    public Trophy findTrophyType(String type) {
        Trophy trophy;

        if(type == null) {
            throw new IllegalArgumentException("Trophy type cannot be null");
        }

        trophy = trophyRepository.findTrophyType(type);

        if(trophy == null) {
            throw new IllegalArgumentException("Trophy was not found or does not exist");
        }
        return trophy;
    }

    public Trophy findTrophyDescription(String description) {
        Trophy trophy;
        if(description == null) {
            throw new IllegalArgumentException("Trophy description cannot be null");
        }

        trophy = trophyRepository.findTrophyDescription(description);

        if(trophy == null) {
            throw new IllegalArgumentException("Description was not found or does not exist");
        }

        return trophy;
    }

}
