package org.example.proyectofinalprogramacion2.Model;

import java.util.ArrayList;
import java.util.List;

public class Combo implements ProductComponent {
    private String comboName;
    private List<ProductComponent> items = new ArrayList<>();
    private double basePrice;
    private double discount;

    public Combo(String comboName, double basePrice, double discount) {
        this.comboName = comboName;
        this.basePrice = basePrice;
        this.discount = discount;
    }

    public void addItem(ProductComponent item) {
        items.add(item);
    }

    @Override
    public double getFinalPrice() {
        double total = basePrice;
        for (ProductComponent item : items) {
            total += item.getFinalPrice();
        }
        return total - discount;
    }

    @Override
    public String getDescription() { return comboName; }

    @Override
    public String toString() {
        return this.comboName + " - $" + getFinalPrice();
    }
}