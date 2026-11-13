package org.example.proyectofinalprogramacion2.View;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.example.proyectofinalprogramacion2.Model.*;
import org.example.proyectofinalprogramacion2.Patterns.Creational.Purchase;
import org.example.proyectofinalprogramacion2.Patterns.Creational.PurchaseBuilder;
import org.example.proyectofinalprogramacion2.Services.DataStore;
import org.example.proyectofinalprogramacion2.Services.PointsService;
import org.example.proyectofinalprogramacion2.Services.PurchaseService;
import org.example.proyectofinalprogramacion2.Services.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ConcessionController {
    @FXML private ListView<ProductComponent> productsList;
    @FXML private TextArea cartArea;
    @FXML private Label totalPointsLabel;
    @FXML private CheckBox usePointsCheckBox; // NUEVO

    private Screening screening;
    private List<Seat> selectedSeats;
    private List<ProductComponent> cart = new ArrayList<>();
    private PurchaseBuilder builder = new PurchaseBuilder();
    private int totalPoints = 0;

    public void initData(Screening screening, List<Seat> seats) {
        this.screening = screening;
        this.selectedSeats = seats;

        builder.withId("COM-" + System.currentTimeMillis())
                .withScreeningDate(screening.getDateTime())
                .withScreening(screening);

        int pointsPerTicket = (int) (screening.getBasePrice() / 10000) * 10;
        totalPoints += pointsPerTicket * seats.size();

        for (Seat seat : seats) {
            builder.addTicket(new Ticket(seat, screening.getBasePrice()));
        }

        productsList.getItems().addAll(DataStore.products);

        productsList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(ProductComponent item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    int points = (int) (item.getFinalPrice() / 10000) * 10;
                    setText(String.format("%s - $%,.0f   (⭐ %d pts)",
                            item.getDescription(), item.getFinalPrice(), points));
                }
            }
        });

        updatePointsLabel();
    }

    private void updatePointsLabel() {
        totalPointsLabel.setText("Puntos totales a ganar: ⭐ " + totalPoints + " pts");
    }

    @FXML
    private void onAddProduct() {
        ProductComponent selected = productsList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            cart.add(selected);
            builder.addProduct(selected);

            int points = (int) (selected.getFinalPrice() / 10000) * 10;
            totalPoints += points;

            cartArea.appendText(String.format("Agregado: %s - $%,.0f (⭐ %d pts)\n",
                    selected.getDescription(), selected.getFinalPrice(), points));

            updatePointsLabel();
        }
    }

    @FXML
    private void onCheckout() {
        Customer currentCustomer = (Customer) SessionManager.getInstance().getCurrentUser();

        if (currentCustomer == null) {
            new Alert(Alert.AlertType.ERROR, "No hay una sesión activa.").show();
            return;
        }

        PointsService pointsService = new PointsService();

        // --- LÓGICA DE REDENCIÓN DE PUNTOS ---
        if (usePointsCheckBox.isSelected()) {
            if (pointsService.canRedeem(currentCustomer, 100)) {
                // Aplicamos el descuento de $5,000 a la compra
                builder.applyPromotion(new Promotion("Descuento por Puntos", 5000));
            } else {
                new Alert(Alert.AlertType.WARNING, "No tienes suficientes puntos (necesitas 100).").show();
                return; // Detenemos el pago
            }
        }

        try {
            // Procesamos el pago (el total ya incluye el descuento si se aplicó)
            Purchase purchase = new PurchaseService().processPurchase(currentCustomer, builder);

            // Si el pago fue exitoso y usó puntos, se los descontamos
            if (usePointsCheckBox.isSelected()) {
                pointsService.deductPoints(currentCustomer, 100);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Compra Exitosa");
            alert.setHeaderText("¡Disfruta tu película: " + screening.getMovie().getTitle() + "!");

            String mensaje = String.format(
                    "Total pagado: $%,.0f\n" +
                            "Saldo restante: $%,.0f\n\n" +
                            "🌟 Has acumulado %d puntos CinemaUQ por esta compra.",
                    purchase.getTotal(),
                    currentCustomer.getCard().getBalance(),
                    purchase.getEarnedPoints()
            );

            alert.setContentText(mensaje);
            alert.showAndWait();

            for (Window window : new ArrayList<>(Window.getWindows())) {
                if (window instanceof Stage) {
                    Stage s = (Stage) window;
                    if (s != WindowManager.getMainStage()) {
                        s.close();
                    }
                }
            }

        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Pago");
            alert.setHeaderText("Transacción Rechazada");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}