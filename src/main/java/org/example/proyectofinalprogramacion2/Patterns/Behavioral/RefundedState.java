package org.example.proyectofinalprogramacion2.Patterns.Behavioral;

import org.example.proyectofinalprogramacion2.Patterns.Creational.Purchase;

public class RefundedState implements PurchaseState {
    @Override
    public void pay(Purchase purchase) {
        throw new IllegalStateException("No se puede pagar una compra ya reembolsada.");
    }

    @Override
    public void cancel(Purchase purchase) {
        throw new IllegalStateException("No se puede cancelar una compra ya reembolsada.");
    }

    @Override
    public void refund(Purchase purchase) {
        throw new IllegalStateException("La compra ya ha sido reembolsada.");
    }

    @Override
    public String getStatusName() {
        return "REFUNDED";
    }
}