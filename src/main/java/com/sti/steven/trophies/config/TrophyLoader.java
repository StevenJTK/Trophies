package com.sti.steven.trophies.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sti.steven.trophies.game.Game;
import com.sti.steven.trophies.game.Trophies;
import com.sti.steven.trophies.game.Trophy;
import com.sti.steven.trophies.interfaces.GameRepository;
import com.sti.steven.trophies.interfaces.TrophyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class TrophyLoader implements CommandLineRunner {

    private final TrophyRepository trophyRepository;
    private final ObjectMapper objectMapper;
    private final GameRepository gameRepository;

    public TrophyLoader(TrophyRepository trophyRepository, ObjectMapper objectMapper, GameRepository gameRepository) {
        this.trophyRepository = trophyRepository;
        this.objectMapper = objectMapper;
        this.gameRepository = gameRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        onAppStartTrophies();
    }

    public void onAppStartTrophies() throws IOException {
        if(trophyRepository.count() == 0) {
            InputStream is = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("trophies.json");

            if (is != null) {
                Map<String, Trophy[]> map = objectMapper.readValue(is, new TypeReference<>() {});
                List<Trophy> trophies = Arrays.asList(map.get("trophies"));

                Game dragonDogma = gameRepository.findByGameName("Dragon's Dogma");
                for (Trophy trophy : trophies) {
                    trophy.setGame(dragonDogma);
                }
                trophyRepository.saveAll(trophies);
                System.out.println("Trophies loaded succesfully!");
            } else {
                System.out.println("No trophies loaded!");
            }
        }
    }
}
