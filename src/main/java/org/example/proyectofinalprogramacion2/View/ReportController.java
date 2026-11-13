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
    @FXML private PieChart salesChart; // NUEVO

    private final ReportService reportService = new ReportService();

    @FXML
    public void initialize() {
        formatCombo.getItems().addAll("TXT", "PDF");
        formatCombo.getSelectionModel().selectFirst();

        // Generar el gráfico al abrir la ventana
        updateVisualChart();
    }

    @FXML
    private void onGenerate() {
        String format = formatCombo.getValue();
        String content = reportService.generateAndExport(format, "Reportes/reporte." + format.toLowerCase());
        reportArea.setText(content);

        // Refrescamos el gráfico por si hubo ventas nuevas mientras la ventana estaba abierta
        updateVisualChart();
    }

    // --- LÓGICA DEL GRÁFICO DE PASTEL ---
    private void updateVisualChart() {
        double ticketRevenue = 0.0;
        double concessionRevenue = 0.0;

        // Recorremos todas las compras del sistema
        for (Purchase p : DataStore.allPurchases) {
            if (p.getStatus() == PurchaseStatus.PAID) {

                // 1. Sumar ingresos por boletos
                for (Ticket t : p.getTickets()) {
                    ticketRevenue += t.getPrice();
                }

                // 2. Sumar ingresos por confitería (Productos y Combos)
                for (ProductComponent item : p.getItems()) {
                    concessionRevenue += item.getFinalPrice();
                }
            }
        }

        // Creamos los datos para el gráfico
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        if (ticketRevenue == 0 && concessionRevenue == 0) {
            pieChartData.add(new PieChart.Data("Sin ventas aún", 1));
        } else {
            pieChartData.add(new PieChart.Data("Boletos ($" + String.format("%,.0f", ticketRevenue) + ")", ticketRevenue));
            pieChartData.add(new PieChart.Data("Confitería ($" + String.format("%,.0f", concessionRevenue) + ")", concessionRevenue));
        }

        // Asignamos los datos al componente visual
        salesChart.setData(pieChartData);
    }
}