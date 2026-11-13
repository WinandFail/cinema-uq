package org.example.proyectofinalprogramacion2.Patterns.Behavioral;

public class AppNotifier implements PurchaseObserver {
    @Override
    public void onPurchaseEvent(String eventType, String message) {
        System.out.println("[NOTIFICACIÓN - " + eventType + "]: " + message);
    }
}