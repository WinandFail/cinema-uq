package org.example.proyectofinalprogramacion2.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion2.Model.Customer;
import org.example.proyectofinalprogramacion2.Model.Screening;
import org.example.proyectofinalprogramacion2.Services.DataStore;
import org.example.proyectofinalprogramacion2.Services.SessionManager;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class CarteleraController {
    @FXML private TableView<Screening> movieTable;
    @FXML private TableColumn<Screening, String> colTitle;
    @FXML private TableColumn<Screening, String> colRoom;
    @FXML private TableColumn<Screening, String> colTime;
    @FXML private TableColumn<Screening, String> colPrice;
    @FXML private TableColumn<Screening, String> colPoints;

    @FXML
    public void initialize() {
        colTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovie().getTitle()));
        colRoom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getName()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        colTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateTime().format(formatter)));

        colPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("$%,.0f", cellData.getValue().getBasePrice())));

        // REGLA: 10 puntos por cada 10000
        colPoints.setCellValueFactory(cellData -> {
            double price = cellData.getValue().getBasePrice();
            int points = (int) (price / 10000) * 10;
            return new SimpleStringProperty("⭐ " + points + " pts");
        });

        ObservableList<Screening> screenings = FXCollections.observableArrayList(DataStore.screenings);
        movieTable.setItems(screenings);
    }

    @FXML
    private void onCheckPoints() {
        Customer customer = (Customer) SessionManager.getInstance().getCurrentUser();
        if (customer == null) return;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Puntos CinemaUQ");
        alert.setHeaderText("Hola, " + customer.getName());
        alert.setContentText("Actualmente tienes ⭐ " + customer.totalPoints() + " puntos disponibles.");
        alert.showAndWait();
    }

    @FXML
    private void onSelectScreening() {
        Screening selected = movieTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Por favor selecciona una función.").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyectofinalprogramacion2/View/seats-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 550);
            StylesManager.applyStyles(scene); // AQUI

            Stage stage = new Stage();
            stage.setTitle("Selección de Asientos - " + selected.getMovie().getTitle());
            stage.setScene(scene);

            SeatsController controller = loader.getController();
            controller.initData(selected);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}