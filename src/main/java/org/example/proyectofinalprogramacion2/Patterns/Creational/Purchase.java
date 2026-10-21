package org.example.proyectofinalprogramacion2.Patterns.Creational;

import org.example.proyectofinalprogramacion2.Model.*;
import org.example.proyectofinalprogramacion2.Model.Combo;

import java.util.ArrayList;
import java.util.List;

public class Purchase {
    private String id;
    private PurchaseStatus status;
    private List<Ticket> tickets = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Combo> combos = new ArrayList<>();
    private List<Promotion> promotions = new ArrayList<>();
    private double total;

    public Purchase() {
        this.status = PurchaseStatus.PENDING;
    }

    public void setId(String id) { this.id = id; }
    public void setStatus(PurchaseStatus status) { this.status = status; }
    public List<Ticket> getTickets() { return tickets; }
    public List<Product> getProducts() { return products; }
    public List<Combo> getCombos() { return combos; }
    public List<Promotion> getPromotions() { return promotions; }

    public double calculateTotal() {
        double sub = 0;
        for (Ticket t : tickets) sub += t.getPrice();
        for (Product p : products) sub += p.getPrice();
        for (Combo c : combos) sub += c.getFinalPrice();
        for (Promotion p : promotions) sub -= p.getDiscount();
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