package com.sti.steven.trophies.game;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String gameName;
    private String gameDescription;
    private String genre;
    private String releaseDate;
    private String developer;

    public Game()  {}

    @Override
    public String toString() {
        return "Game [id=" + id + ", gameName=" + gameName + ", description=" + gameDescription + ", genre=" + genre +
                ", releaseDate=" + releaseDate + ", developer=" + developer + "]";
    }

    @OneToMany(mappedBy = "game")
    private Set<Trophy> trophies = new HashSet();

    public Set<Trophy> getTrophies() {
        return trophies;
    }

    public int getId() {
        return id;
    }

    public String getGameName() {
        return gameName;
    }

    public String getDescription() {
        return gameDescription;
    }

    public String getGenre() {
        return genre;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setDescription(String description) {
        this.gameDescription = description;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

}
