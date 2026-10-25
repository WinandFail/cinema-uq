package org.example.proyectofinalprogramacion2.Patterns.Structural;

import org.example.proyectofinalprogramacion2.Model.Customer;
import org.example.proyectofinalprogramacion2.Model.CardStatus; // <-- Importante importar esto
import org.example.proyectofinalprogramacion2.Services.PaymentService;

public class PaymentProxy implements PaymentService {
    private RealPaymentService realService = new RealPaymentService();
    private Customer customer;

    public PaymentProxy(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean processPayment(double amount) {

        if (customer.getCard() == null || customer.getCard().getStatus() != CardStatus.ACTIVE) {
            System.out.println("Error: Tarjeta inactiva.");
            return false;
        }
        if (customer.getCard().getBalance() < amount) {
            System.out.println("Error: Saldo insuficiente.");
            return false;
        }
        return realService.processPayment(amount);
    }
}