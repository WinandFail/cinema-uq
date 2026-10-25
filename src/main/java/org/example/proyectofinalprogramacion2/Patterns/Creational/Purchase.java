package org.example.proyectofinalprogramacion2.Patterns.Creational;

import org.example.proyectofinalprogramacion2.Model.*;
import org.example.proyectofinalprogramacion2.Model.Combo;

import java.util.ArrayList;
import java.util.List;

public class Purchase {
    private String id;
    private PurchaseStatus status;
    private List<Ticket> tickets = new ArrayList<>();

    private List<ProductComponent> items = new ArrayList<>();

    private List<Promotion> promotions = new ArrayList<>();
    private double total;

    public Purchase() {
        this.status = PurchaseStatus.PENDING;
    }

    public void setId(String id) { this.id = id; }
    public void setStatus(PurchaseStatus status) { this.status = status; }
    public List<Ticket> getTickets() { return tickets; }
    public List<ProductComponent> getItems() { return items; } // Nuevo getter
    public List<Promotion> getPromotions() { return promotions; }

    public void addItem(ProductComponent item) {
        this.items.add(item);
    }

    public double calculateTotal() {
        double sub = 0;

        for (Ticket t : tickets) {
            sub += t.getPrice();
        }

        for (ProductComponent c : items) {
            sub += c.getFinalPrice(); // Ya no dará error
        }

        for (Promotion p : promotions) {
            sub -= p.getDiscount();
        }


        double vat = CinemaConfig.getInstance().getVat();
        this.total = sub + (sub * vat);

        return total;
    }

    public double getTotal() { return total; }
    public String getId() { return id; }
    public PurchaseStatus getStatus() { return status; }

    @Override
    public String toString() {
        return "Compra{" + id + ", total=" + total + ", Estado=" + status + "}";
    }
}