package org.example.proyectofinalprogramacion2.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import org.example.proyectofinalprogramacion2.Model.*;
import org.example.proyectofinalprogramacion2.Patterns.Creational.*;
import org.example.proyectofinalprogramacion2.Model.Combo;
import org.example.proyectofinalprogramacion2.Patterns.Structural.Product;
import org.example.proyectofinalprogramacion2.Services.PurchaseService;
import org.example.proyectofinalprogramacion2.Services.PointsService;

import java.io.IOException;
import java.time.LocalDateTime;

public class MainController {

    @FXML private Label statusLabel;
    @FXML private TextArea outputArea;

    private final Customer ana = new Customer("C-001", "Ana", "ana@uq.edu.co", "1234", "3001234567");
    private final Admin admin = new Admin("A-001", "Admin", "admin@uq.edu.co", "admin", "Coordinator");

    @FXML
    public void initialize() {
        admin.rechargeCard(ana.getCard(), 200_000);
        statusLabel.setText("Bienvenido, " + ana.getName() + " | Balance: $" + ana.getCard().getBalance());
    }

    @FXML
    private void onOpenAdapter() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/org/example/proyectofinalprogramacion2/View/adapter-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Patrón Adapter");
        stage.setScene(new Scene(loader.load(), 500, 300));
        stage.show();
    }

    @FXML
    private void onOpenProxy() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/org/example/proyectofinalprogramacion2/View/proxy-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Patrón Proxy");
        stage.setScene(new Scene(loader.load(), 500, 300));
        stage.show();
    }

    @FXML
    private void onOpenDecorator() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/org/example/proyectofinalprogramacion2/View/decorator-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Patrón Decorator");
        stage.setScene(new Scene(loader.load(), 500, 300));
        stage.show();
    }

    @FXML
    private void onOpenComposite() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/org/example/proyectofinalprogramacion2/View/composite-view.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Patrón Composite");
        stage.setScene(new Scene(loader.load(), 500, 300));
        stage.show();
    }
    @FXML
    private void onBuyTickets() {
        Movie movie = new Movie("Interestelar", "Ciencia Ficción", 120, "PG-13");
        Room room = new Room("Sala 2", 96);
        Screening screening = new Screening(movie, room, LocalDateTime.now().plusDays(2), 16_000);

        PurchaseBuilder builder = new PurchaseBuilder()
                .withId("COM-" + System.currentTimeMillis())
                .addTicket(new Ticket(new Seat("D", 7), screening.getBasePrice()))
                .addTicket(new Ticket(new Seat("D", 8), screening.getBasePrice()))
                .addProduct(new Product("Palomitas Grandes", 18_000))
                .addCombo(new Combo("Combo parejas", 35000, 5000))
                .applyPromotion(new Promotion("Jueves 2x1", 8_000));

        Purchase purchase = new PurchaseService().processPurchase(ana, builder);

        outputArea.appendText(" Compra " + purchase.getId()
                + " | Total: $" + purchase.getTotal()
                + " | Puntos: " + ana.totalPoints() + "\n");
        statusLabel.setText("Balance: $" + ana.getCard().getBalance());
    }

    @FXML
    private void onRedeemPoints() {
        int redeemed = new PointsService().redeem(ana, 100);
        outputArea.appendText(" Puntos redimidos: " + redeemed + "\n");
    }

    @FXML
    private void onOpenReports() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                MainController.class.getResource("/org/example/proyectofinalprogramacion2/View/report-view.fxml")
        );
        Stage stage = new Stage();
        stage.setTitle("Reportes");
        stage.setScene(new Scene(loader.load(), 600, 400));
        stage.show();
    }
}