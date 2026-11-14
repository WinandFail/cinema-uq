package org.example.proyectofinalprogramacion2.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import org.example.proyectofinalprogramacion2.Model.PurchaseStatus;
import org.example.proyectofinalprogramacion2.Model.Ticket;
import org.example.proyectofinalprogramacion2.Model.ProductComponent;
import org.example.proyectofinalprogramacion2.Patterns.Creational.Purchase;
import org.example.proyectofinalprogramacion2.Report.ReportService;
import org.example.proyectofinalprogramacion2.Services.DataStore;

public class ReportController {

    @FXML private ComboBox<String> formatCombo;
    @FXML private TextArea reportArea;
    @FXML private PieChart salesChart;

    private final ReportService reportService = new ReportService();

    @FXML
    public void initialize() {
        formatCombo.getItems().addAll("TXT", "PDF");
        formatCombo.getSelectionModel().selectFirst();


        updateVisualChart();
    }

    @FXML
    private void onGenerate() {
        String format = formatCombo.getValue();
        String content = reportService.generateAndExport(format, "Reportes/reporte." + format.toLowerCase());
        reportArea.setText(content);


        updateVisualChart();
    }


    private void updateVisualChart() {
        double ticketRevenue = 0.0;
        double concessionRevenue = 0.0;

        for (Purchase p : DataStore.allPurchases) {
            if (p.getStatus() == PurchaseStatus.PAID) {

                for (Ticket t : p.getTickets()) {
                    ticketRevenue += t.getPrice();
                }

                for (ProductComponent item : p.getItems()) {
                    concessionRevenue += item.getFinalPrice();
                }
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        if (ticketRevenue == 0 && concessionRevenue == 0) {
            pieChartData.add(new PieChart.Data("Sin ventas aún", 1));
        } else {
            pieChartData.add(new PieChart.Data("Boletos ($" + String.format("%,.0f", ticketRevenue) + ")", ticketRevenue));
            pieChartData.add(new PieChart.Data("Confitería ($" + String.format("%,.0f", concessionRevenue) + ")", concessionRevenue));
        }

        salesChart.setData(pieChartData);
    }
}