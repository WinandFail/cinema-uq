package org.example.proyectofinalprogramacion2.Patterns.Creational;

import org.example.proyectofinalprogramacion2.Model.*;
import org.example.proyectofinalprogramacion2.Model.Combo;

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

    // CAMBIO AQUÍ: Usamos addItem en lugar de getProducts().add()
    public PurchaseBuilder addProduct(ProductComponent p) {
        purchase.addItem(p);
        return this;
    }

    // CAMBIO AQUÍ: Usamos addItem en lugar de getCombos().add()
    public PurchaseBuilder addCombo(ProductComponent c) {
        purchase.addItem(c);
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