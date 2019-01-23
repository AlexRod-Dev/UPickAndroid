package com.example.alex.upick.Models;

public class CurrentSong {

    private String track_id;

    public CurrentSong(String track_id) {
        this.track_id = track_id;
    }

    public String getTrack_id() {
        return track_id;
    }

    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }
}
