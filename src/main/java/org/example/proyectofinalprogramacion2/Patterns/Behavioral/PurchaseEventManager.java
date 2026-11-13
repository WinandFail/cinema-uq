package org.example.proyectofinalprogramacion2.Patterns.Behavioral;

import java.util.ArrayList;
import java.util.List;

public class PurchaseEventManager {
    private static PurchaseEventManager instance;
    private List<PurchaseObserver> observers = new ArrayList<>();

    private PurchaseEventManager() {}

    public static PurchaseEventManager getInstance() {
        if (instance == null) {
            instance = new PurchaseEventManager();
        }
        return instance;
    }

    public void subscribe(PurchaseObserver observer) {
        observers.add(observer);
    }

    public void unsubscribe(PurchaseObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String eventType, String message) {
        for (PurchaseObserver observer : observers) {
            observer.onPurchaseEvent(eventType, message);
        }
    }
}