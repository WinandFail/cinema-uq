package org.example.proyectofinalprogramacion2.Model;

import java.time.LocalDateTime;

public class Transaction {
    private LocalDateTime date;
    private TransactionType type;
    private double amount;
    private String description;

    public Transaction(TransactionType type, double amount, String description) {
        this.date = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    @Override
    public String toString() {
        return date + " | " + type + " | $" + amount + " | " + description;
    }
}