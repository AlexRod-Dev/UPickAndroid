package com.example.alex.upick.Models;

public class Queue {

    private int id;
    private String track_id;
    private int user_id;
    private int votes;

    public Queue(int id, String track_id, int user_id, int votes) {
        this.id = id;
        this.track_id = track_id;
        this.user_id = user_id;
        this.votes = votes;
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

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
