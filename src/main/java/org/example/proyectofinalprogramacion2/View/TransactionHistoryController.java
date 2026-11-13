package org.example.proyectofinalprogramacion2.View;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion2.Model.Customer;
import org.example.proyectofinalprogramacion2.Model.Transaction;
import org.example.proyectofinalprogramacion2.Services.SessionManager;

public class TransactionHistoryController {

    @FXML private ListView<String> transactionList;

    @FXML
    public void initialize() {
        Customer customer = (Customer) SessionManager.getInstance().getCurrentUser();

        if (customer != null && customer.getCard() != null) {
            // Recorremos las transacciones y las agregamos a la lista
            for (Transaction t : customer.getCard().getTransactions()) {
                transactionList.getItems().add(t.toString());
            }
        }

        if (transactionList.getItems().isEmpty()) {
            transactionList.getItems().add("No hay movimientos registrados.");
        }
    }

    @FXML
    private void onClose() {
        ((Stage) transactionList.getScene().getWindow()).close();
    }
}