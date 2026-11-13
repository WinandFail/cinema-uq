package org.example.proyectofinalprogramacion2.Patterns.Behavioral;

import org.example.proyectofinalprogramacion2.Patterns.Creational.Purchase;

public class CancelledState implements PurchaseState {
    @Override
    public void pay(Purchase purchase) {
        throw new IllegalStateException("No se puede pagar una compra cancelada.");
    }

    @Override
    public void cancel(Purchase purchase) {
        throw new IllegalStateException("La compra ya está cancelada.");
    }

    @Override
    public void refund(Purchase purchase) {
        throw new IllegalStateException("No se puede reembolsar una compra cancelada.");
    }

    @Override
    public String getStatusName() {
        return "CANCELLED";
    }
}