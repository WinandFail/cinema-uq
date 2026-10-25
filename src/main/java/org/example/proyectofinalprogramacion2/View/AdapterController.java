package org.example.proyectofinalprogramacion2.View;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.proyectofinalprogramacion2.Patterns.Structural.PdfReportAdapter;

public class AdapterController {
    @FXML private TextField pathField;
    @FXML private TextArea logArea;

    @FXML
    private void generatePdf() {
        String path = pathField.getText().isEmpty() ? "reporte.pdf" : pathField.getText();
        PdfReportAdapter adapter = new PdfReportAdapter();
        adapter.generate("Contenido del reporte desde la interfaz", path);
        logArea.appendText("Se generó el PDF usando el Adapter en: " + path + "\n");
    }
}