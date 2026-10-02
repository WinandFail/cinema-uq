package org.example.proyectofinalprogramacion2.Model;

public class Promotion {
    private String name;
    private double discount;

    public Promotion(String name, double discount) {
        this.name = name;
        this.discount = discount;
    }

    public double getDiscount() { return discount; }
    public String getName() { return name; }
}