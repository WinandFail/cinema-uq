package org.example.proyectofinalprogramacion2.Patterns.Structural;

public abstract class RefundDecorator implements Purchase {
    protected Purchase purchase;
    public RefundDecorator(Purchase purchase) { this.purchase = purchase; }
    @Override
    public double getTotalPaid() { return purchase.getTotalPaid(); }
    @Override
    public double getRefundAmount() { return purchase.getRefundAmount(); }
}
