package org.example.proyectofinalprogramacion2.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.proyectofinalprogramacion2.Model.Room;
import org.example.proyectofinalprogramacion2.Model.Screening;
import org.example.proyectofinalprogramacion2.Model.Seat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SeatsController {
    @FXML private GridPane seatsGrid;
    @FXML private Label selectedSeatsLabel;
    @FXML private Label totalPointsLabel;

    private Screening screening;
    private List<Seat> selectedSeats = new ArrayList<>();

    public void initData(Screening screening) {
        this.screening = screening;
        generateSeats();
    }

    private void generateSeats() {
        Room room = screening.getRoom();
        int rows = 5;
        int cols = 8;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                String seatRow = String.valueOf((char) ('A' + row));
                int seatNum = col + 1;

                Seat seat = new Seat(seatRow, seatNum);
                room.getSeats().add(seat);

                Button btn = new Button(seatRow + seatNum);

                btn.setPrefSize(45, 45);


                String estiloBase = "-fx-font-family: 'Segoe UI', Arial, sans-serif; " +
                        "-fx-font-size: 13px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 0; ";

                if ((row == 1 && col == 1) || (row == 2 && col == 4) || (row == 3 && col == 6)) {

                    seat.occupy();
                    btn.setStyle(estiloBase + "-fx-background-color: #9E9E9E; -fx-text-fill: white;");
                    btn.setDisable(true);
                } else {

                    btn.setStyle(estiloBase + "-fx-background-color: #4CAF50; -fx-text-fill: white;");

                    btn.setOnAction(e -> {
                        if (selectedSeats.contains(seat)) {
                            selectedSeats.remove(seat);
                            btn.setStyle(estiloBase + "-fx-background-color: #4CAF50; -fx-text-fill: white;");
                        } else {
                            selectedSeats.add(seat);
                            btn.setStyle(estiloBase + "-fx-background-color: #FFC107; -fx-text-fill: black;");
                        }
                        updateLabels();
                    });
                }

                seatsGrid.add(btn, col, row);
            }
        }
    }

    private void updateLabels() {
        selectedSeatsLabel.setText("Asientos seleccionados: " + selectedSeats.size());


        int pointsPerTicket = (int) (screening.getBasePrice() / 10000) * 10;
        int totalPoints = pointsPerTicket * selectedSeats.size();

        totalPointsLabel.setText("Puntos a ganar: ⭐ " + totalPoints + " pts");
    }

    @FXML
    private void onContinueToConcession() {
        if (selectedSeats.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Debes seleccionar al menos un asiento.").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/proyectofinalprogramacion2/View/concession-view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 550);
            StylesManager.applyStyles(scene);

            Stage stage = new Stage();
            stage.setTitle("Confitería - " + screening.getMovie().getTitle());
            stage.setScene(scene);

            ConcessionController controller = loader.getController();
            controller.initData(screening, selectedSeats);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}