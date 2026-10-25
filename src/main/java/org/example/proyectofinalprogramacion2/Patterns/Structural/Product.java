package org.example.proyectofinalprogramacion2.Patterns.Structural;

import org.example.proyectofinalprogramacion2.Model.ProductComponent;

public class Product implements ProductComponent {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double getFinalPrice() {
        return this.price;
    }

    @Override
    public String getDescription() {
        return this.name;
    }
}