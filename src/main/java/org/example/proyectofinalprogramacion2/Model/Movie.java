package org.example.proyectofinalprogramacion2.Model;

public class Movie {
    private String title;
    private String genre;
    private int durationMin;
    private String rating;

    public Movie(String title, String genre, int durationMin, String rating) {
        this.title = title;
        this.genre = genre;
        this.durationMin = durationMin;
        this.rating = rating;
    }

    public String getTitle() { return title; }
}