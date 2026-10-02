package org.example.proyectofinalprogramacion2.Services;

import org.example.proyectofinalprogramacion2.Model.Customer;
import org.example.proyectofinalprogramacion2.Model.Payment;
import org.example.proyectofinalprogramacion2.Model.Points;
import org.example.proyectofinalprogramacion2.Model.PurchaseStatus;
import org.example.proyectofinalprogramacion2.Patterns.Creational.CinemaConfig;
import org.example.proyectofinalprogramacion2.Patterns.Creational.Purchase;
import org.example.proyectofinalprogramacion2.Patterns.Creational.PurchaseBuilder;

public class PurchaseService {

    public Purchase processPurchase(Customer customer, PurchaseBuilder builder) {
        Purchase purchase = builder.build();

        customer.getCard().debit(purchase.getTotal(), "Compra " + purchase.getId());

        Payment payment = new Payment(purchase.getTotal());
        payment.approve();

        int earnedPoints = (int) (purchase.getTotal() / 1000) * CinemaConfig.getInstance().getPointsPerThousand();
        customer.addPoints(new Points(earnedPoints));

        purchase.setStatus(PurchaseStatus.PAID);
        return purchase;
    }
}