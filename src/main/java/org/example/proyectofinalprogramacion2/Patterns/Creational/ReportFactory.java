package org.example.proyectofinalprogramacion2.Patterns.Creational;

public class ReportFactory {
    public static Report create(String type) {
        return switch (type.toUpperCase()) {
            case "TXT" -> new TxtReport();
            case "PDF" -> new PdfReport();
            default -> throw new IllegalArgumentException("Formato no soportado " + type);
        };
    }
}