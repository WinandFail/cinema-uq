package org.example.proyectofinalprogramacion2.Model;

public class Payment {
    private double amount;
    private PaymentStatus status;

    public Payment(double amount) {
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
    }

    public void approve() { this.status = PaymentStatus.APPROVED; }
    public void reject() { this.status = PaymentStatus.REJECTED; }
    public PaymentStatus getStatus() { return status; }
}