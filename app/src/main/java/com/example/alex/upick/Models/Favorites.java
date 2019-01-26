package com.example.alex.upick.Models;

import java.sql.Timestamp;

public class Favorites {

    private int id;
    private String track_id;
    private int user_id;
    private String added_at;
    private String artists;
    private String name;

    public Favorites(){}

    public Favorites(int id, String track_id, int user_id, String added_at, String artists, String name) {
        this.id = id;
        this.track_id = track_id;
        this.user_id = user_id;
        this.added_at = added_at;
        this.artists = artists;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrack_id() {
        return track_id;
    }

    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAdded_at() {
        return added_at;
    }

    public void setAdded_at(String added_at) {
        this.added_at = added_at;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
