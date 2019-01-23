package com.example.alex.upick.Models;

public class Track {

    private int id;
    private String name;
    private int duration_ms;
    private String artists;
    private String images;

    public Track(int id, String name, int duration_ms, String artists, String images) {
        this.id = id;
        this.name = name;
        this.duration_ms = duration_ms;
        this.artists = artists;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration_ms() {
        return duration_ms;
    }

    public void setDuration_ms(int duration_ms) {
        this.duration_ms = duration_ms;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
