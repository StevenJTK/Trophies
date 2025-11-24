package interfaces;

import game.Trophy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrophyRepository extends JpaRepository<Trophy, Integer> {
    Trophy findByTrophyName(String trophyName);
    Trophy findTrophyType(String trophyType);
    Trophy findTrophyDescription(String trophyDescription);
}
