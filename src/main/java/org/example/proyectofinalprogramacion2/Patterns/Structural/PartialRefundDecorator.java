package org.example.proyectofinalprogramacion2.Patterns.Structural;

public class PartialRefundDecorator extends RefundDecorator {
    public PartialRefundDecorator(Purchase purchase) { super(purchase); }

    @Override
    public double getRefundAmount() {
        return purchase.getTotalPaid() * 0.80;
    }
}
