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
    private double subTotal; // Guarda el valor antes del IVA
    private LocalDateTime screeningDate;
    private int earnedPoints;

    // Relaciones para el Administrador
    private Customer customer;
    private Screening screening;

    // Patrón State
    private PurchaseState currentState;

    public Purchase() {
        this.status = PurchaseStatus.PENDING;
        this.currentState = new PendingState();
    }

    // --- MÉTODOS DEL PATRÓN STATE ---
    public void setState(PurchaseState state) { this.currentState = state; }
    public PurchaseState getCurrentState() { return currentState; }

    public void pay() { currentState.pay(this); }
    public void cancel() { currentState.cancel(this); }
    public void refund() { currentState.refund(this); }

    // --- LÓGICA DE NEGOCIO ---
    public double calculateTotal() {
        double sub = 0;

        // Sumamos boletos
        for (Ticket t : tickets) {
            sub += t.getPrice();
        }

        // Sumamos productos y combos
        for (ProductComponent c : items) {
            sub += c.getFinalPrice();
        }

        // Restamos descuentos/promociones (ej. Canje de puntos)
        for (Promotion p : promotions) {
            sub -= p.getDiscount();
        }

        // Validación: Evitar que el subtotal quede negativo
        if (sub < 0) {
            sub = 0;
        }

        this.subTotal = sub; // Guardamos el subtotal real

        // Aplicamos el IVA (19%)
        double vat = CinemaConfig.getInstance().getVat();
        this.total = sub + (sub * vat);

        return total;
    }

    public void addItem(ProductComponent item) {
        this.items.add(item);
    }

    // --- GETTERS Y SETTERS ---
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