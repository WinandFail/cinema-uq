package org.example.proyectofinalprogramacion2.Model;

import java.util.ArrayList;
import java.util.List;

public class Combo implements ProductComponent {
    private String comboName;
    private List<ProductComponent> items = new ArrayList<>();

    public void addItem(ProductComponent item) { items.add(item); }

    @Override
    public double getPrice() {
        double total = 0;
        for (ProductComponent item : items) {
            total += item.getPrice();
        }

        return total * 0.9;
    }
    @Override
    public String getDescription() { return comboName; }
}