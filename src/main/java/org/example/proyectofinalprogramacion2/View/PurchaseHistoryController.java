package org.example.proyectofinalprogramacion2.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion2.Model.Customer;
import org.example.proyectofinalprogramacion2.Patterns.Creational.Purchase;
import org.example.proyectofinalprogramacion2.Services.DataStore;
import org.example.proyectofinalprogramacion2.Services.SessionManager;

import java.time.format.DateTimeFormatter;

public class PurchaseHistoryController {

    @FXML private TableView<Purchase> purchaseTable;
    @FXML private TableColumn<Purchase, String> colId;
    @FXML private TableColumn<Purchase, String> colDate;
    @FXML private TableColumn<Purchase, String> colTotal;
    @FXML private TableColumn<Purchase, String> colPoints;
    @FXML private TableColumn<Purchase, String> colStatus;

    @FXML
    public void initialize() {
        Customer customer = (Customer) SessionManager.getInstance().getCurrentUser();

        colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        colDate.setCellValueFactory(data -> {
            if (data.getValue().getScreeningDate() != null) {
                return new SimpleStringProperty(data.getValue().getScreeningDate().format(formatter));
            }
            return new SimpleStringProperty("N/A");
        });

        colTotal.setCellValueFactory(data -> new SimpleStringProperty(String.format("$%,.0f", data.getValue().getTotal())));
        colPoints.setCellValueFactory(data -> new SimpleStringProperty("⭐ " + data.getValue().getEarnedPoints()));
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus().toString()));

        purchaseTable.setItems(FXCollections.observableArrayList(DataStore.getPurchasesByCustomer(customer)));
    }

    @FXML
    private void onClose() {
        ((Stage) purchaseTable.getScene().getWindow()).close();
    }
}