package com.sti.steven.trophies.game;

import jakarta.persistence.*;
import com.sti.steven.trophies.product.User;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Trophy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String trophyName;
    private Trophies trophyType;
    private String trophyDescription;


    public Trophy() {}

    @ManyToOne()
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToMany(mappedBy = "trophies")
    private Set<User> user = new HashSet<User>();

    public int getId() {
        return id;
    }

    public String getTrophyName() {
        return trophyName;
    }

    public Trophies getTrophies() {
        return trophyType;
    }

    public String getTrophyDescription() {
        return trophyDescription;
    }

    public void setTrophies(Trophies trophies) {
        this.trophyType = trophies;
    }

    public void setTrophyName(String trophyName) {
        this.trophyName = trophyName;
    }
    public void setTrophyDescription(String trophyDescription) {
        this.trophyDescription = trophyDescription;
    }
}
