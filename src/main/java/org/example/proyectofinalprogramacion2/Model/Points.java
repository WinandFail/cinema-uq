package org.example.proyectofinalprogramacion2.Model;

import java.time.LocalDate;

public class Points {
    private int amount;
    private LocalDate obtainedDate;
    private LocalDate expirationDate;

    public Points(int amount) {
        this.amount = amount;
        this.obtainedDate = LocalDate.now();
        this.expirationDate = obtainedDate.plusYears(1);
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    public int getAmount() { return amount; }

    // NUEVO: Para poder restar puntos al redimir
    public void setAmount(int amount) { this.amount = amount; }

    public LocalDate getExpirationDate() { return expirationDate; }
}