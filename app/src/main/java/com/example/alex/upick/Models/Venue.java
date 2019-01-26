package com.example.alex.upick.Models;

public class Venue {
    private int id;
    private String name;
    private String venue_owner_id;
    private String description;
    private String twitterLink;
    private String facebookLink;
    private String instagramLink;
    private Double posLat;
    private Double posLong;
    private String token;
    private String queue;
    private String range;
    private String imagepath;

    public Venue(int id, String name, String venue_owner_id, String description, String twitterLink, String facebookLink, String instagramLink, Double posLat, Double posLong, String token, String queue, String range, String imagepath) {
        this.id = id;
        this.name = name;
        this.venue_owner_id = venue_owner_id;
        this.description = description;
        this.twitterLink = twitterLink;
        this.facebookLink = facebookLink;
        this.instagramLink = instagramLink;
        this.posLat = posLat;
        this.posLong = posLong;
        this.token = token;
        this.queue = queue;
        this.range = range;
        this.imagepath = imagepath;
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

    public String getVenue_owner_id() {
        return venue_owner_id;
    }

    public void setVenue_owner_id(String venue_owner_id) {
        this.venue_owner_id = venue_owner_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public Double getPosLat() {
        return posLat;
    }

    public void setPosLat(Double posLat) {
        this.posLat = posLat;
    }

    public Double getPosLong() {
        return posLong;
    }

    public void setPosLong(Double posLong) {
        this.posLong = posLong;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
