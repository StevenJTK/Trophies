package com.sti.steven.trophies.interfaces;

import com.sti.steven.trophies.game.Trophies;
import com.sti.steven.trophies.game.Trophy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrophyRepository extends JpaRepository<Trophy, Integer> {
    Optional<Trophy> findByGame_GameNameAndTrophyType(String gameName, Trophies type);
    Optional<Trophy> findByGame_GameNameAndTrophyDescription(String gameName, String trophyDescription);
    Optional<Trophy> findByGame_GameNameAndTrophyName(String gameName, String trophyName);
}
