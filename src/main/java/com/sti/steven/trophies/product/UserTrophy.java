package com.sti.steven.trophies.product;

import com.sti.steven.trophies.game.Trophy;
import jakarta.persistence.*;

@Entity
public class UserTrophy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "trophy_id", nullable = false)
    private Trophy trophy;

    private boolean completed = false;
}
