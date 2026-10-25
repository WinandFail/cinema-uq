package org.example.proyectofinalprogramacion2.Model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private int capacity;

    public Room(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }
    // En Room.java
    private List<Seat> seats = new ArrayList<>();

    public List<Seat> getSeats() {
        return seats;
    }
    public String getName() { return name; }
}