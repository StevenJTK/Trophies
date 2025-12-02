package com.sti.steven.trophies.interfaces;

import com.sti.steven.trophies.game.Trophies;
import com.sti.steven.trophies.game.Trophy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrophyRepository extends JpaRepository<Trophy, Integer> {
    Trophy findByTrophyName(String trophyName);
    Trophy findByTrophyType(Trophies trophyType);
    Trophy findByTrophyDescription(String trophyDescription);
    Optional<Trophy> findByGame_GameNameAndTrophyName(String gameName, String trophyName);
}
