package org.example.proyectofinalprogramacion2.Patterns.Structural;

import org.example.proyectofinalprogramacion2.Model.ProductComponent;

public class Product implements ProductComponent {
    private String name;
    private double price;
    // Constructor, getters...
    @Override
    public double getPrice() { return price; }
    @Override
    public String getDescription() { return name; }
}


