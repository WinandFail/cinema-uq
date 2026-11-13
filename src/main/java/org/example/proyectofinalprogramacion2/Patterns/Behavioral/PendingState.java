package org.example.proyectofinalprogramacion2.Patterns.Behavioral;

import org.example.proyectofinalprogramacion2.Model.PurchaseStatus;
import org.example.proyectofinalprogramacion2.Patterns.Creational.Purchase;

public class PendingState implements PurchaseState {
    @Override
    public void pay(Purchase purchase) {
        purchase.setStatus(PurchaseStatus.PAID);
        purchase.setState(new PaidState());
    }
    @Override
    public void cancel(Purchase purchase) {
        purchase.setStatus(PurchaseStatus.CANCELLED);
        purchase.setState(new CancelledState());
    }
    @Override
    public void refund(Purchase purchase) {
        throw new IllegalStateException("No se puede reembolsar una compra pendiente");
    }
    @Override
    public String getStatusName() { return "PENDING"; }
}