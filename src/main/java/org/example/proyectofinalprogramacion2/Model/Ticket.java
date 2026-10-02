package org.example.proyectofinalprogramacion2.Model;

public class Ticket {
    private Seat seat;
    private double price;

    public Ticket(Seat seat, double price) {
        this.seat = seat;
        this.price = price;
    }

    public double getPrice() { return price; }
}