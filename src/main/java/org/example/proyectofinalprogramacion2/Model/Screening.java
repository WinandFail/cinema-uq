package org.example.proyectofinalprogramacion2.Model;

import java.time.LocalDateTime;

public class Screening {
    private Movie movie;
    private Room room;
    private LocalDateTime dateTime;
    private double basePrice;

    public Screening(Movie movie, Room room, LocalDateTime dateTime, double basePrice) {
        this.movie = movie;
        this.room = room;
        this.dateTime = dateTime;
        this.basePrice = basePrice;
    }

    public Movie getMovie() { return movie; }
    public double getBasePrice() { return basePrice; }
    public LocalDateTime getDateTime() { return dateTime; }
}