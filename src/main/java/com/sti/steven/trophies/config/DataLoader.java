package com.sti.steven.trophies.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sti.steven.trophies.game.Game;
import com.sti.steven.trophies.interfaces.GameRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class DataLoader implements CommandLineRunner {

    private final GameRepository gameRepository;
    private final ObjectMapper objectMapper;

    public DataLoader(GameRepository gameRepository, ObjectMapper objectMapper) {
        this.gameRepository = gameRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        onApplicationStart();
    }

    public void onApplicationStart() throws Exception {
        if(gameRepository.count() == 0) {
            InputStream is = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("game.json");

            if (is != null) {
                Map<String, Game[]> map = objectMapper.readValue(is, new TypeReference<Map<String, Game[]>>() {});
                List<Game> games = Arrays.asList(map.get("game"));
                gameRepository.saveAll(games);
                System.out.println("Games loaded successfully!");
            } else {
                System.out.println("JSON file not found");
            }
        }
    }
}
