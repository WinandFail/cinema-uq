package org.example.proyectofinalprogramacion2.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PurchaseController {

    @FXML private Label purchaseLabel;

    @FXML
    public void initialize() {
        purchaseLabel.setText("Modulo de compra listo");
    }
}