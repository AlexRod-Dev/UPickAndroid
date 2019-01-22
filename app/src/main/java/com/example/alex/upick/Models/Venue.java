package com.example.alex.upick.Models;

public class Venue {
    private int id;
    private String name;
    private String description;
    private String twitterlink;
    private String facebooklink;
    private String instagramlink;
    private Double poslat;
    private Double postlong;
    private String token;
    private String queue;
    private String range;
    private String imagePath;


    public Venue(int id, String name, String description, String twitterlink, String facebooklink, String instagramlink, Double poslat, Double postlong, String token, String queue, String range, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.twitterlink = twitterlink;
        this.facebooklink = facebooklink;
        this.instagramlink = instagramlink;
        this.poslat = poslat;
        this.postlong = postlong;
        this.token = token;
        this.queue = queue;
        this.range = range;
        this.imagePath = imagePath;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTwitterlink() {
        return twitterlink;
    }

    public void setTwitterlink(String twitterlink) {
        this.twitterlink = twitterlink;
    }

    public String getFacebooklink() {
        return facebooklink;
    }

    public void setFacebooklink(String facebooklink) {
        this.facebooklink = facebooklink;
    }

    public String getInstagramlink() {
        return instagramlink;
    }

    public void setInstagramlink(String instagramlink) {
        this.instagramlink = instagramlink;
    }

    public Double getPoslat() {
        return poslat;
    }

    public void setPoslat(Double poslat) {
        this.poslat = poslat;
    }

    public Double getPostlong() {
        return postlong;
    }

    public void setPostlong(Double postlong) {
        this.postlong = postlong;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
