package org.example.proyectofinalprogramacion2.Patterns.Creational;

public class PdfReport implements Report {
    @Override
    public String generate() {
        return "=== CinemaUQ Reporte (PDF) ===\nVentas: $5,556,000";
    }

    @Override
    public void export(String path) {
        System.out.println("Exportando PDF a: " + path);
    }
}