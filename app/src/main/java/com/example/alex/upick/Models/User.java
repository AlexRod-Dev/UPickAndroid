package com.example.alex.upick.Models;

public class User {

    private int id;
    private String username;
    private String auth_key;
    private String email;
    private String spotify_access_token;
    private String spotify_refresh_token;
    private int status;


    public User(int id, String username, String auth_key, String email, String spotify_access_token, String spotify_refresh_token, int status) {
        this.id = id;
        this.username = username;
        this.auth_key = auth_key;
        this.email = email;
        this.spotify_access_token = spotify_access_token;
        this.spotify_refresh_token = spotify_refresh_token;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpotify_access_token() {
        return spotify_access_token;
    }

    public void setSpotify_access_token(String spotify_access_token) {
        this.spotify_access_token = spotify_access_token;
    }

    public String getSpotify_refresh_token() {
        return spotify_refresh_token;
    }

    public void setSpotify_refresh_token(String spotify_refresh_token) {
        this.spotify_refresh_token = spotify_refresh_token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
