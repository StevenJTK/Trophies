package com.sti.steven.trophies.dto;

public class UserDTO {
    private String username;
    private String password;
    private String email;
    private Integer id;

    public UserDTO(String username, String password, String email, Integer id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public UserDTO()  {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
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

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
