package org.example.proyectofinalprogramacion2.View;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.example.proyectofinalprogramacion2.Model.Combo;
import org.example.proyectofinalprogramacion2.Patterns.Structural.Product;

public class CompositeController {
    @FXML private TextArea logArea;

    @FXML
    private void testComposite() {

        Product palomitas = new Product("Palomitas", 15000);
        Product soda = new Product("Soda", 8000);


        Combo combo = new Combo("Combo Pareja", 20000, 2000);
        combo.addItem(palomitas);
        combo.addItem(soda);

        logArea.appendText("Producto individual (Palomitas): $" + palomitas.getFinalPrice() + "\n");
        logArea.appendText("Combo (Palomitas + Soda con descuento): $" + combo.getFinalPrice() + "\n");
        logArea.appendText("\nEl Composite permite tratar al Combo y al Producto de la misma forma.\n");
    }
}