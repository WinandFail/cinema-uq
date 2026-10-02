package org.example.proyectofinalprogramacion2.Patterns.Creational;

import org.example.proyectofinalprogramacion2.Model.*;

public class PurchaseBuilder {
    private Purchase purchase = new Purchase();

    public PurchaseBuilder withId(String id) {
        purchase.setId(id);
        return this;
    }

    public PurchaseBuilder addTicket(Ticket t) {
        purchase.getTickets().add(t);
        return this;
    }

    public PurchaseBuilder addProduct(Product p) {
        purchase.getProducts().add(p);
        return this;
    }

    public PurchaseBuilder addCombo(Combo c) {
        purchase.getCombos().add(c);
        return this;
    }

    public PurchaseBuilder applyPromotion(Promotion p) {
        purchase.getPromotions().add(p);
        return this;
    }

    public Purchase build() {
        purchase.calculateTotal();
        return purchase;
    }
}