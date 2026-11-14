package org.example.proyectofinalprogramacion2.Services;

import org.example.proyectofinalprogramacion2.Model.*;
import org.example.proyectofinalprogramacion2.Patterns.Behavioral.PurchaseEventManager;
import org.example.proyectofinalprogramacion2.Patterns.Creational.Purchase;
import org.example.proyectofinalprogramacion2.Patterns.Creational.PurchaseBuilder;
import org.example.proyectofinalprogramacion2.Patterns.Structural.PaymentProxy;

public class PurchaseService {
    private PurchaseEventManager eventManager;

    public PurchaseService() {
        this.eventManager = PurchaseEventManager.getInstance();
    }

    public Purchase processPurchase(Customer customer, PurchaseBuilder builder) {
        builder.withCustomer(customer);
        Purchase purchase = builder.build();

        PaymentProxy proxy = new PaymentProxy(customer);
        if (!proxy.processPayment(purchase.getTotal())) {
            throw new RuntimeException("Pago rechazado. Verifique saldo o estado de tarjeta.");
        }

        customer.getCard().debit(purchase.getTotal(), "Compra " + purchase.getId());
        purchase.pay();


        int earnedPoints = (int) (purchase.getSubTotal() / 10000) * 10;

        customer.addPoints(new Points(earnedPoints));
        purchase.setEarnedPoints(earnedPoints);

        DataStore.addPurchase(purchase);

        String message = String.format("Compra %s por $%,.0f realizada con éxito. Ganaste %d puntos.",
                purchase.getId(), purchase.getTotal(), earnedPoints);
        eventManager.notifyObservers("COMPRA_EXITOSA", message);

        return purchase;
    }
}