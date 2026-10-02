package org.example.proyectofinalprogramacion2.Model;

public class Combo {
    private String name;
    private double price;
    private double discount;

    public Combo(String name, double price, double discount) {
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    public double getFinalPrice() { return price - discount; }
    public String getName() { return name; }
}