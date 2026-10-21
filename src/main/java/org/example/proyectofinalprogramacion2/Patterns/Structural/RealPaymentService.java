package org.example.proyectofinalprogramacion2.Patterns.Structural;

import org.example.proyectofinalprogramacion2.Model.Customer;
import org.example.proyectofinalprogramacion2.Services.PaymentService;

public class RealPaymentService implements PaymentService {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Pago procesado exitosamente.");
        return true;
    }
}

