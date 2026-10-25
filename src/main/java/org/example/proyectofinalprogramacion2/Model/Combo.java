package org.example.proyectofinalprogramacion2.Model;

import java.util.ArrayList;
import java.util.List;

public class Combo implements ProductComponent {
    private String comboName;
    private List<ProductComponent> items = new ArrayList<>();

    // Nuevos atributos para manejar el precio y descuento
    private double basePrice;
    private double discount;

    // Constructor actualizado con los 3 parámetros
    public Combo(String comboName, double basePrice, double discount) {
        this.comboName = comboName;
        this.basePrice = basePrice;
        this.discount = discount;
    }

    // Si quieres mantener un constructor vacío o solo con nombre por si acaso
    public Combo(String comboName) {
        this.comboName = comboName;
        this.basePrice = 0;
        this.discount = 0;
    }

    public void addItem(ProductComponent item) {
        items.add(item);
    }

    @Override
    public double getFinalPrice() {
        double total = basePrice;

        // Sumamos el precio de los items que tenga el combo (si los tiene)
        for (ProductComponent item : items) {
            total += item.getFinalPrice();
        }

        // Aplicamos el descuento (ej. si discount es 5000, se resta)
        return total - discount;
    }

    @Override
    public String getDescription() {
        return comboName;
    }
}