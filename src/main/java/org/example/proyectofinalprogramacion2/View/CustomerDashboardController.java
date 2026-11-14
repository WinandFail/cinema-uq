package org.example.proyectofinalprogramacion2.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion2.Model.Cancellation;
import org.example.proyectofinalprogramacion2.Model.Customer;
import org.example.proyectofinalprogramacion2.Model.PurchaseStatus;
import org.example.proyectofinalprogramacion2.Patterns.Behavioral.PurchaseEventManager;
import org.example.proyectofinalprogramacion2.Patterns.Behavioral.PurchaseObserver;
import org.example.proyectofinalprogramacion2.Patterns.Creational.Purchase;
import org.example.proyectofinalprogramacion2.Services.DataStore;
import org.example.proyectofinalprogramacion2.Services.SessionManager;

import java.io.IOException;
import java.util.List;

public class CustomerDashboardController implements PurchaseObserver {

    @FXML private Label statusLabel;
    @FXML private TextArea outputArea;

    private Customer customer;

    @FXML
    public void initialize() {
        customer = (Customer) SessionManager.getInstance().getCurrentUser();
        updateBalanceLabel();
        outputArea.appendText("Sesión iniciada como Cliente.\n");
        PurchaseEventManager.getInstance().subscribe(this);
    }

    public void updateBalanceLabel() {
        statusLabel.setText(String.format("Bienvenido(a), %s | Saldo: $%,.0f | Puntos: ⭐ %d",
                customer.getName(), customer.getCard().getBalance(), customer.totalPoints()));
    }

    @Override
    public void onPurchaseEvent(String eventType, String message) {
        if ("COMPRA_EXITOSA".equals(eventType)) {
            updateBalanceLabel();
            outputArea.appendText(message + "\n");
        }
    }

    @FXML
    private void onOpenCartelera() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyectofinalprogramacion2/View/cartelera-view.fxml"));
            Scene scene = new Scene(loader.load(), 650, 500);
            StylesManager.applyStyles(scene);

            Stage stage = new Stage();
            stage.setTitle("Cartelera CinemaUQ");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            outputArea.appendText("Error al abrir la cartelera.\n");
        }
    }

    @FXML
    private void onCancelPurchase() {
        List<Purchase> purchases = DataStore.getPurchasesByCustomer(customer);
        if (purchases.isEmpty()) {
            outputArea.appendText("No tienes compras registradas para cancelar.\n");
            return;
        }

        Purchase lastPurchase = purchases.get(purchases.size() - 1);
        if (lastPurchase.getStatus() == PurchaseStatus.CANCELLED || lastPurchase.getStatus() == PurchaseStatus.REFUNDED) {
            outputArea.appendText("La última compra ya fue cancelada o reembolsada.\n");
            return;
        }

        double refundAmount = Cancellation.calculateRefund(lastPurchase.getTotal(), lastPurchase.getScreeningDate());
        if (refundAmount > 0) {
            customer.getCard().recharge(refundAmount);
            try {
                lastPurchase.cancel();
                outputArea.appendText(" Cancelación exitosa de la compra " + lastPurchase.getId() + ".\n");
                outputArea.appendText(String.format(" Reembolso abonado a tu tarjeta: $%,.0f\n", refundAmount));
                updateBalanceLabel();
            } catch (IllegalStateException e) {
                outputArea.appendText("Error: " + e.getMessage() + "\n");
            }
        } else {
            outputArea.appendText(" No se puede cancelar la compra " + lastPurchase.getId() + ".\n");
            outputArea.appendText("Motivo: Faltan menos de 2 horas para la función.\n");
        }
    }

    @FXML
    private void onOpenPurchaseHistory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyectofinalprogramacion2/View/purchase-history-view.fxml"));
            Scene scene = new Scene(loader.load(), 700, 450);
            StylesManager.applyStyles(scene);

            Stage stage = new Stage();
            stage.setTitle("Historial de Compras");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onOpenTransactionHistory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyectofinalprogramacion2/View/transaction-history-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 450);
            StylesManager.applyStyles(scene);

            Stage stage = new Stage();
            stage.setTitle("Movimientos de Tarjeta");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}