package org.example.proyectofinalprogramacion2.View;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import org.example.proyectofinalprogramacion2.Model.*;
import org.example.proyectofinalprogramacion2.Patterns.Creational.Purchase;
import org.example.proyectofinalprogramacion2.Report.ReportService;
import org.example.proyectofinalprogramacion2.Services.DataStore;

import java.util.HashMap;
import java.util.Map;

public class ReportController {
    @FXML private ComboBox<String> formatCombo;
    @FXML private TextArea reportArea;
    @FXML private PieChart salesChart;
    @FXML private BarChart<String, Number> movieBarChart;
    @FXML private LineChart<String, Number> trendLineChart;

    private final ReportService reportService = new ReportService();

    @FXML
    public void initialize() {
        formatCombo.getItems().addAll("TXT", "PDF");
        formatCombo.getSelectionModel().selectFirst();
        updateAllCharts();
    }

    @FXML
    private void onGenerate() {
        String format = formatCombo.getValue();
        String filePath = "Reportes/reporte_cinemauq." + format.toLowerCase();
        String content = reportService.generateAndExport(format, filePath);
        reportArea.setText(content);
        updateAllCharts();
        new Alert(Alert.AlertType.INFORMATION, "Reporte exportado exitosamente en: " + filePath).show();
    }

    private void updateAllCharts() {
        double ticketRev = 0, concessionRev = 0;
        Map<String, Integer> ticketsByMovie = new HashMap<>();
        XYChart.Series<String, Number> lineSeries = new XYChart.Series<>();
        lineSeries.setName("Facturación por Compra ($)");

        int idx = 1;
        for (Purchase p : DataStore.allPurchases) {
            if (p.getStatus() == PurchaseStatus.PAID) {
                for (Ticket t : p.getTickets()) ticketRev += t.getPrice();
                for (ProductComponent item : p.getItems()) concessionRev += item.getFinalPrice();

                if (p.getScreening() != null) {
                    String title = p.getScreening().getMovie().getTitle();
                    ticketsByMovie.put(title, ticketsByMovie.getOrDefault(title, 0) + p.getTickets().size());
                }
                lineSeries.getData().add(new XYChart.Data<>("Compra #" + idx++, p.getTotal()));
            }
        }


        salesChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("Boletos", ticketRev),
                new PieChart.Data("Confitería", concessionRev)
        ));

        if (movieBarChart != null) {
            movieBarChart.getData().clear();
            XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
            barSeries.setName("Boletos Vendidos");
            ticketsByMovie.forEach((movie, count) -> barSeries.getData().add(new XYChart.Data<>(movie, count)));
            movieBarChart.getData().add(barSeries);
        }

        if (trendLineChart != null) {
            trendLineChart.getData().clear();
            trendLineChart.getData().add(lineSeries);
        }
    }
}