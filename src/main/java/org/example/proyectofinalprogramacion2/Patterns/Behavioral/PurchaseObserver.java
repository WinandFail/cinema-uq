package org.example.proyectofinalprogramacion2.Patterns.Behavioral;

public interface PurchaseObserver {
    void onPurchaseEvent(String eventType, String message);
}