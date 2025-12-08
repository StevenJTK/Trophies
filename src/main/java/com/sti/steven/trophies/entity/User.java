package com.sti.steven.trophies.entity;

import com.sti.steven.trophies.game.Trophy;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trophy_user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private boolean isEnabled = false;

    public User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Username [id=" + id + ", username=" + username;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    @ManyToMany
    @JoinTable(
            name = "user_trophy",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn (name = "trophy_id")
    )

    private Set<Trophy> trophies = new HashSet<>();

    public Set<Trophy> getTrophies() {
        return trophies;
    }

    public Set<Trophy> getCompletedTrophies() {
        return trophies;
    }

    public void completeTrophy(Trophy trophy) {
        if(!trophies.contains(trophy)) {
            trophies.add(trophy);
        }
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword()  {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}