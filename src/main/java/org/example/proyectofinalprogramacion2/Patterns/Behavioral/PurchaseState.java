package org.example.proyectofinalprogramacion2.Patterns.Behavioral;

import org.example.proyectofinalprogramacion2.Patterns.Creational.Purchase;

public interface PurchaseState {
    void pay(Purchase purchase);
    void cancel(Purchase purchase);
    void refund(Purchase purchase);
    String getStatusName();
}