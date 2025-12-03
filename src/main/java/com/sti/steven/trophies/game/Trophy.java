package com.sti.steven.trophies.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import com.sti.steven.trophies.product.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trophies")
public class Trophy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String trophyName;
    @JsonProperty("type")
    private Trophies trophyType;
    @JsonProperty("description")
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

    public String getTrophyType() {
        return trophyType.toString();
    }

    public void setTrophyType(Trophies trophyType) {
        this.trophyType = trophyType;
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

    public void setGame(Game dragonDogma) {
        this.game = dragonDogma;
    }
}
