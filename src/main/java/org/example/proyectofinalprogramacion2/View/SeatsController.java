package org.example.proyectofinalprogramacion2.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion2.Model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SeatsController {
    @FXML private GridPane seatsGrid;
    @FXML private Label selectedSeatsLabel;

    private Screening screening;
    private List<Seat> selectedSeats = new ArrayList<>();


    public void initData(Screening screening) {
        this.screening = screening;
        generateSeats();
    }

    private void generateSeats() {
        // Generamos una cuadrícula de 5x8 (40 asientos) de prueba
        Room room = screening.getRoom();
        int rows = 5;
        int cols = 8;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                String seatRow = String.valueOf((char) ('A' + row));
                int seatNum = col + 1;

                // Creamos el asiento en el modelo
                Seat seat = new Seat(seatRow, seatNum);
                room.getSeats().add(seat); // Asumiendo que Room tiene una lista de asientos

                Button btn = new Button(seatRow + seatNum);
                btn.setPrefSize(40, 40);
                btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Verde = Disponible

                // Lógica al hacer clic en el asiento
                btn.setOnAction(e -> {
                    if (selectedSeats.contains(seat)) {
                        selectedSeats.remove(seat);
                        btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                    } else {
                        selectedSeats.add(seat);
                        btn.setStyle("-fx-background-color: #FFC107; -fx-text-fill: black;"); // Amarillo = Seleccionado
                    }
                    selectedSeatsLabel.setText("Asientos seleccionados: " + selectedSeats.size());
                });

                seatsGrid.add(btn, col, row);
            }
        }
    }

    @FXML
    private void onContinueToConcession() {
        if (selectedSeats.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Debes seleccionar al menos un asiento.").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyectofinalprogramacion2/View/concession-view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Confitería");
            stage.setScene(new Scene(loader.load(), 600, 500));

            ConcessionController controller = loader.getController();

            controller.initData(screening, selectedSeats);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}