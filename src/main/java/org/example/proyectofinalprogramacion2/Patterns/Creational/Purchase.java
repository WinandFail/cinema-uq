package org.example.proyectofinalprogramacion2.Patterns.Creational;

import org.example.proyectofinalprogramacion2.Model.*;
import org.example.proyectofinalprogramacion2.Patterns.Behavioral.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Purchase {
    private String id;
    private PurchaseStatus status;
    private List<Ticket> tickets = new ArrayList<>();
    private List<ProductComponent> items = new ArrayList<>();
    private List<Promotion> promotions = new ArrayList<>();

    private double total;
    private double subTotal;
    private LocalDateTime screeningDate;
    private int earnedPoints;

    private Customer customer;
    private Screening screening;

    private PurchaseState currentState;

    public Purchase() {
        this.status = PurchaseStatus.PENDING;
        this.currentState = new PendingState();
    }

    public void setState(PurchaseState state) { this.currentState = state; }
    public PurchaseState getCurrentState() { return currentState; }

    public void pay() { currentState.pay(this); }
    public void cancel() { currentState.cancel(this); }
    public void refund() { currentState.refund(this); }


    public double calculateTotal() {
        double sub = 0;


        for (Ticket t : tickets) {
            sub += t.getPrice();
        }

        for (ProductComponent c : items) {
            sub += c.getFinalPrice();
        }

        for (Promotion p : promotions) {
            sub -= p.getDiscount();
        }

        if (sub < 0) {
            sub = 0;
        }

        this.subTotal = sub;


        double vat = CinemaConfig.getInstance().getVat();
        this.total = sub + (sub * vat);

        return total;
    }

    public void addItem(ProductComponent item) {
        this.items.add(item);
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public PurchaseStatus getStatus() { return status; }
    public void setStatus(PurchaseStatus status) { this.status = status; }

    public List<Ticket> getTickets() { return tickets; }
    public List<ProductComponent> getItems() { return items; }
    public List<Promotion> getPromotions() { return promotions; }

    public double getTotal() { return total; }
    public double getSubTotal() { return subTotal; }

    public LocalDateTime getScreeningDate() { return screeningDate; }
    public void setScreeningDate(LocalDateTime screeningDate) { this.screeningDate = screeningDate; }

    public int getEarnedPoints() { return earnedPoints; }
    public void setEarnedPoints(int earnedPoints) { this.earnedPoints = earnedPoints; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Screening getScreening() { return screening; }
    public void setScreening(Screening screening) { this.screening = screening; }
}