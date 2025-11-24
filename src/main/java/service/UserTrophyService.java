package service;

import game.Trophy;
import interfaces.TrophyRepository;
import interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import product.User;

@Service
public class UserTrophyService {

    private final UserRepository userRepository;
    private final TrophyRepository trophyRepository;

    @Autowired
    public UserTrophyService(UserRepository userRepository, TrophyRepository trophyRepository) {
        this.userRepository = userRepository;
        this.trophyRepository = trophyRepository;
    }

    public void grantTrophyToUser(User user, Trophy trophy) {
        if (user == null || trophy == null) {
            throw new IllegalArgumentException("User or Trophy cannot be null");
        }

        if (user.getTrophies().contains(trophy)) {
            throw new IllegalArgumentException("User already has this trophy");
        }

        user.getTrophies().add(trophy);
    }

}
