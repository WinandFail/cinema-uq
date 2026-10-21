package org.example.proyectofinalprogramacion2.Patterns.Structural;
import org.example.proyectofinalprogramacion2.Report.ReportGenerator;

public class ExternalPdfLibrary {
    public void createPdfDocument(String text, String path) {
        System.out.println("Generando PDF externo en: " + path);
    }
}

