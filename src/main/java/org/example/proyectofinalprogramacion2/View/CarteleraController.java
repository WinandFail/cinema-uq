package org.example.proyectofinalprogramacion2.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion2.Model.Movie;
import org.example.proyectofinalprogramacion2.Model.Room;
import org.example.proyectofinalprogramacion2.Model.Screening;
import org.example.proyectofinalprogramacion2.Model.Seat;

import java.io.IOException;
import java.time.LocalDateTime;

public class CarteleraController {
    @FXML private TableView<Screening> movieTable;
    @FXML private TableColumn<Screening, String> colTitle;
    @FXML private TableColumn<Screening, String> colRoom;
    @FXML private TableColumn<Screening, LocalDateTime> colTime;
    @FXML private TableColumn<Screening, Double> colPrice;

    @FXML
    public void initialize() {
        colTitle.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMovie().getTitle()));
        colRoom.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRoom().getName()));
        colTime.setCellValueFactory(new PropertyValueFactory<>("datetime")); // Asumiendo que tienes getDatetime()
        colPrice.setCellValueFactory(new PropertyValueFactory<>("basePrice"));

        Movie movie1 = new Movie("Interestelar", "Ciencia Ficción", 120, "PG-13");
        Room room1 = new Room("Sala 1", 50);
        Screening s1 = new Screening(movie1, room1, LocalDateTime.now().plusDays(1), 16000);

        Movie movie2 = new Movie("El Padrino", "Drama", 175, "R");
        Room room2 = new Room("Sala 2", 50);
        Screening s2 = new Screening(movie2, room2, LocalDateTime.now().plusDays(1), 15000);

        ObservableList<Screening> screenings = FXCollections.observableArrayList(s1, s2);
        movieTable.setItems(screenings);
    }

    @FXML
    private void onSelectScreening() {
        Screening selected = movieTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Por favor selecciona una función.");
            alert.show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyectofinalprogramacion2/View/seats-view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Selección de Asientos - " + selected.getMovie().getTitle());
            stage.setScene(new Scene(loader.load(), 600, 500));


            SeatsController controller = loader.getController();
            controller.initData(selected);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}