package org.example.proyectofinalprogramacion2.Patterns.Creational;

public class TxtReport implements Report {
    @Override
    public String generate() {
        return "=== CinemaUQ Reporte (TXT) ===\nVentas: $5,556,000";
    }

    @Override
    public void export(String path) {
        System.out.println("Exportando TXT a: " + path);
    }
}