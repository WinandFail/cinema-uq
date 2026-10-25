package org.example.proyectofinalprogramacion2.View;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.proyectofinalprogramacion2.Model.Customer;
import org.example.proyectofinalprogramacion2.Model.VirtualCard;
import org.example.proyectofinalprogramacion2.Patterns.Structural.PaymentProxy;

public class ProxyController {
    @FXML private TextField amountField;
    @FXML private TextArea logArea;

    @FXML
    private void processPayment() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            Customer customer = new Customer("C-002", "Test", "test@test.com", "123", "300");
            customer.setCard(new VirtualCard("1234-5678", 100000)); // Saldo de 100.000

            PaymentProxy proxy = new PaymentProxy(customer);
            boolean success = proxy.processPayment(amount);

            logArea.appendText(success ? " Pago aprobado por el Proxy\n" : " Pago rechazado por el Proxy\n");
        } catch (Exception e) {
            logArea.appendText("Error: Ingresa un número válido.\n");
        }
    }
}