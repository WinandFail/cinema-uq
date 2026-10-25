package org.example.proyectofinalprogramacion2.View;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.proyectofinalprogramacion2.Patterns.Structural.BasePurchase;
import org.example.proyectofinalprogramacion2.Patterns.Structural.PartialRefundDecorator;
import org.example.proyectofinalprogramacion2.Patterns.Structural.Purchase;

public class DecoratorController {
    @FXML private TextField totalField;
    @FXML private TextArea logArea;

    @FXML
    private void calculateRefund() {
        try {
            double total = Double.parseDouble(totalField.getText());
            Purchase basePurchase = new BasePurchase(total);
            Purchase refundDecorator = new PartialRefundDecorator(basePurchase);

            logArea.appendText("Total Pagado: $" + basePurchase.getTotalPaid() + "\n");
            logArea.appendText("Reembolso (80%): $" + refundDecorator.getRefundAmount() + "\n");
        } catch (Exception e) {
            logArea.appendText("Error: Ingresa un número válido.\n");
        }
    }
}