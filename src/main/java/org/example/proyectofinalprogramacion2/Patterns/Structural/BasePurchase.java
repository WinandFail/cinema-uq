package org.example.proyectofinalprogramacion2.Patterns.Structural;

public class BasePurchase implements Purchase {
    private double totalPaid;
    public BasePurchase(double totalPaid) { this.totalPaid = totalPaid; }

    @Override
    public double getTotalPaid() { return totalPaid; }
    @Override
    public double getRefundAmount() { return totalPaid; }
}