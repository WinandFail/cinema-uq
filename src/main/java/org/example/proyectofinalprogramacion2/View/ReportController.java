package org.example.proyectofinalprogramacion2.View;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import org.example.proyectofinalprogramacion2.Report.ReportService;

public class ReportController {

    @FXML private ComboBox<String> formatCombo;
    @FXML private TextArea reportArea;

    private final ReportService reportService = new ReportService();

    @FXML
    public void initialize() {
        formatCombo.getItems().addAll("TXT", "PDF");
        formatCombo.getSelectionModel().selectFirst();
    }

    @FXML
    private void onGenerate() {
        String format = formatCombo.getValue();
        String content = reportService.generateAndExport(format, "Reportes/reporte." + format.toLowerCase());
        reportArea.setText(content);
    }
}