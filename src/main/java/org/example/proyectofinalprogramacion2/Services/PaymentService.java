package org.example.proyectofinalprogramacion2.Services;

import org.example.proyectofinalprogramacion2.Model.VirtualCard;

public class PaymentService {
    public boolean validatePayment(VirtualCard card, double amount) {
        try {
            card.validateBalance(amount);
            return true;
        } catch (RuntimeException e) {
            System.out.println("Pago rechazado: " + e.getMessage());
            return false;
        }
    }
}