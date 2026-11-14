package org.example.proyectofinalprogramacion2.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion2.Model.*;
import org.example.proyectofinalprogramacion2.Patterns.Creational.Purchase;
import org.example.proyectofinalprogramacion2.Patterns.Structural.Product;
import org.example.proyectofinalprogramacion2.Services.DataStore;
import org.example.proyectofinalprogramacion2.Services.SessionManager;

import java.time.LocalDateTime;

public class AdminDashboardController {

    @FXML private ComboBox<Customer> customerCombo;
    @FXML private TextField amountField;
    @FXML private TextArea logArea;

    @FXML private ComboBox<Screening> screeningCombo;
    @FXML private TextArea cancelLogArea;

    @FXML private TextField movieTitleField;
    @FXML private TextField moviePriceField;

    @FXML private TextField prodNameField;
    @FXML private TextField prodPriceField;

    @FXML private TextField comboNameField;
    @FXML private TextField comboBasePriceField;
    @FXML private TextField comboDiscountField;

    private Admin admin;

    @FXML
    public void initialize() {
        admin = (Admin) SessionManager.getInstance().getCurrentUser();

        customerCombo.getItems().addAll(DataStore.getAllCustomers());
        customerCombo.setCellFactory(param -> new ListCell<>() {
            @Override protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty); setText(empty || item == null ? null : item.getName() + " (" + item.getId() + ")");
            }
        });
        customerCombo.setButtonCell(customerCombo.getCellFactory().call(null));

        updateScreeningCombo();
    }

    private void updateScreeningCombo() {
        screeningCombo.getItems().clear();
        screeningCombo.getItems().addAll(DataStore.screenings);
        screeningCombo.setCellFactory(param -> new ListCell<>() {
            @Override protected void updateItem(Screening item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getMovie().getTitle() + " - " + item.getDateTime().toString());
            }
        });
        screeningCombo.setButtonCell(screeningCombo.getCellFactory().call(null));
    }

    @FXML
    private void onRechargeCard() {
        Customer selected = customerCombo.getValue();
        String amountStr = amountField.getText().trim();

        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Seleccione un cliente primero.").show();
            return;
        }
        if (amountStr.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "El campo de monto está vacío.").show();
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                new Alert(Alert.AlertType.WARNING, "El monto debe ser mayor a 0.").show();
                return;
            }

            admin.rechargeCard(selected.getCard(), amount);
            logArea.appendText(String.format("Recarga de $%,.0f a %s\n", amount, selected.getName()));
            amountField.clear();
            new Alert(Alert.AlertType.INFORMATION, "Recarga realizada con éxito.").show();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "El monto debe ser un número válido (no letras).").show();
        }
    }


    @FXML
    private void onCancelScreening() {
        Screening selected = screeningCombo.getValue();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Seleccione una función para cancelar.").show();
            return;
        }

        int reembolsos = 0;
        for (Purchase p : DataStore.allPurchases) {
            if (p.getScreening() != null && p.getScreening().equals(selected) && p.getStatus() == PurchaseStatus.PAID) {
                p.getCustomer().getCard().recharge(p.getTotal());
                p.cancel();
                reembolsos++;
            }
        }

        DataStore.screenings.remove(selected);
        updateScreeningCombo();
        cancelLogArea.appendText("Función cancelada. Se reembolsó a " + reembolsos + " clientes.\n");
    }


    @FXML
    private void onAddMovie() {
        String title = movieTitleField.getText().trim();
        String priceStr = moviePriceField.getText().trim();

        if (title.isEmpty() || priceStr.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Por favor, llene todos los campos.").show();
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            if (price <= 0) {
                new Alert(Alert.AlertType.WARNING, "El precio debe ser mayor a 0.").show();
                return;
            }

            Movie newMovie = new Movie(title, "General", 120, "PG");
            Room newRoom = new Room("Sala Auto", 50);
            Screening newScreening = new Screening(newMovie, newRoom, LocalDateTime.now().plusDays(1), price);

            DataStore.screenings.add(newScreening);
            updateScreeningCombo();

            cancelLogArea.appendText("Película agregada: " + title + "\n");
            movieTitleField.clear(); moviePriceField.clear();
            new Alert(Alert.AlertType.INFORMATION, "Película agregada correctamente.").show();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "El precio debe ser un número válido (no letras).").show();
        }
    }


    @FXML
    private void onAddProduct() {
        String name = prodNameField.getText().trim();
        String priceStr = prodPriceField.getText().trim();

        if (name.isEmpty() || priceStr.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Por favor, llene todos los campos.").show();
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            if (price <= 0) {
                new Alert(Alert.AlertType.WARNING, "El precio debe ser mayor a 0.").show();
                return;
            }

            Product newProduct = new Product(name, price);
            DataStore.products.add(newProduct);

            new Alert(Alert.AlertType.INFORMATION, "Producto agregado exitosamente.").show();
            prodNameField.clear(); prodPriceField.clear();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "El precio debe ser un número válido (no letras).").show();
        }
    }


    @FXML
    private void onAddCombo() {
        String name = comboNameField.getText().trim();
        String basePriceStr = comboBasePriceField.getText().trim();
        String discountStr = comboDiscountField.getText().trim();

        if (name.isEmpty() || basePriceStr.isEmpty() || discountStr.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Por favor, llene todos los campos.").show();
            return;
        }

        try {
            double basePrice = Double.parseDouble(basePriceStr);
            double discount = Double.parseDouble(discountStr);

            if (basePrice <= 0 || discount < 0) {
                new Alert(Alert.AlertType.WARNING, "El precio debe ser mayor a 0 y el descuento no puede ser negativo.").show();
                return;
            }

            Combo newCombo = new Combo(name, basePrice, discount);
            DataStore.products.add(newCombo);

            new Alert(Alert.AlertType.INFORMATION, "Combo agregado exitosamente.").show();
            comboNameField.clear(); comboBasePriceField.clear(); comboDiscountField.clear();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Los precios deben ser números válidos (no letras).").show();
        }
    }

    @FXML
    private void onOpenReports() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyectofinalprogramacion2/View/report-view.fxml"));
        Scene scene = new Scene(loader.load(), 800, 500);
        StylesManager.applyStyles(scene);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}