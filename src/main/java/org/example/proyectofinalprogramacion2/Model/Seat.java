package org.example.proyectofinalprogramacion2.Model;

public class Seat {
    private String row;
    private int number;
    private SeatStatus status;

    public Seat(String row, int number) {
        this.row = row;
        this.number = number;
        this.status = SeatStatus.AVAILABLE;
    }

    public void block() { this.status = SeatStatus.BLOCKED; }
    public void release() { this.status = SeatStatus.AVAILABLE; }
    public void occupy() { this.status = SeatStatus.OCCUPIED; }

    public String getCode() { return row + number; }
    public SeatStatus getStatus() { return status; }
}