package com.example.alex.upick.Models;

public class News {

    private int id;
    private int venueId;
    private String message;
    private String state;

    public News(int id, int venueId, String message, String state) {
        this.id = id;
        this.venueId = venueId;
        this.message = message;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
