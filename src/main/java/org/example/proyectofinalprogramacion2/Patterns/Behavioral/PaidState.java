package org.example.proyectofinalprogramacion2.Patterns.Behavioral;

import org.example.proyectofinalprogramacion2.Model.PurchaseStatus;
import org.example.proyectofinalprogramacion2.Patterns.Creational.Purchase;

public class PaidState implements PurchaseState {
    @Override
    public void pay(Purchase purchase) {
        throw new IllegalStateException("La compra ya está pagada");
    }
    @Override
    public void cancel(Purchase purchase) {
        purchase.setStatus(PurchaseStatus.CANCELLED);
        purchase.setState(new CancelledState());
    }
    @Override
    public void refund(Purchase purchase) {
        purchase.setStatus(PurchaseStatus.REFUNDED);
        purchase.setState(new RefundedState());
    }
    @Override
    public String getStatusName() { return "PAID"; }
}