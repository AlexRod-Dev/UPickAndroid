package com.example.alex.upick.Models;

public class Music {

    private String nome,autor,time,likes;
    private int img;

    public Music(String nome, String autor, String time, String likes, int img) {
        this.nome = nome;
        this.autor = autor;
        this.time = time;
        this.likes = likes;
        this.img = img;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
