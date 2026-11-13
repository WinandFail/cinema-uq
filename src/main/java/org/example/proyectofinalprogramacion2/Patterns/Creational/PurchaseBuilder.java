package org.example.proyectofinalprogramacion2.Patterns.Creational;

import org.example.proyectofinalprogramacion2.Model.*;
import java.time.LocalDateTime;

public class PurchaseBuilder {
    private Purchase purchase = new Purchase();

    public PurchaseBuilder withId(String id) {
        purchase.setId(id);
        return this;
    }

    public PurchaseBuilder withScreeningDate(LocalDateTime date) {
        purchase.setScreeningDate(date);
        return this;
    }

    public PurchaseBuilder withCustomer(Customer customer) {
        purchase.setCustomer(customer);
        return this;
    }

    public PurchaseBuilder withScreening(Screening screening) {
        purchase.setScreening(screening);
        return this;
    }

    public PurchaseBuilder addTicket(Ticket t) {
        purchase.getTickets().add(t);
        return this;
    }

    public PurchaseBuilder addProduct(ProductComponent p) {
        purchase.addItem(p);
        return this;
    }

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