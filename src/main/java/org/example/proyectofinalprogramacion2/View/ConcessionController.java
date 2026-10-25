package org.example.proyectofinalprogramacion2.View;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.example.proyectofinalprogramacion2.Model.*;
import org.example.proyectofinalprogramacion2.Patterns.Creational.*;
import org.example.proyectofinalprogramacion2.Patterns.Structural.PaymentProxy;
import org.example.proyectofinalprogramacion2.Patterns.Structural.Product;
import org.example.proyectofinalprogramacion2.Services.PurchaseService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConcessionController {
    @FXML private ListView<ProductComponent> productsList;
    @FXML private TextArea cartArea;

    private Screening screening;
    private List<Seat> selectedSeats;
    private List<ProductComponent> cart = new ArrayList<>();
    private PurchaseBuilder builder = new PurchaseBuilder();

    public void initData(Screening screening, List<Seat> seats) {
        this.screening = screening;
        this.selectedSeats = seats;


        builder.withId("COM-" + System.currentTimeMillis());
        for (Seat seat : seats) {
            builder.addTicket(new Ticket(seat, screening.getBasePrice()));
        }

        // Cargar productos de prueba (Confitería)
        Combo comboPareja = new Combo("Combo Pareja", 25000, 3000);
        comboPareja.addItem(new Product("Palomitas", 15000));
        comboPareja.addItem(new Product("Soda", 10000));

        productsList.getItems().addAll(
                new Product("Palomitas Grandes", 18000),
                new Product("Soda Mediana", 8000),
                comboPareja
        );
    }

    @FXML
    private void onAddProduct() {
        ProductComponent selected = productsList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            cart.add(selected);
            cartArea.appendText("Agregado: " + selected.getDescription() + " - $" + selected.getFinalPrice() + "\n");
        }
    }

    @FXML
    private void onCheckout() {

        for (ProductComponent p : cart) {
            builder.addProduct(p); // Gracias a SOLID, ProductComponent acepta Productos y Combos
        }

        Purchase purchase = builder.build();


        Customer ana = new Customer("C-001", "Ana", "ana@uq.edu.co", "1234", "3001234567");
        ana.setCard(new VirtualCard("1234-5678", 200000)); // Saldo de $200,000

        PaymentProxy proxy = new PaymentProxy(ana);
        boolean pagoExitoso = proxy.processPayment(purchase.getTotal());

        if (pagoExitoso) {
            // Realizar el débito real a la tarjeta
            ana.getCard().debit(purchase.getTotal(), "Pago de compra " + purchase.getId());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Compra Exitosa");
            alert.setHeaderText("¡Disfruta tu película!");
            alert.setContentText("Total pagado: $" + purchase.getTotal() + "\nSaldo restante: $" + ana.getCard().getBalance());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Pago");
            alert.setHeaderText("Transacción Rechazada");
            alert.setContentText("Verifica el saldo de tu tarjeta virtual.");
            alert.showAndWait();
        }
    }
}