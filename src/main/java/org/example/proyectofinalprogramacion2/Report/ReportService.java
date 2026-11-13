package org.example.proyectofinalprogramacion2.Report;

import org.example.proyectofinalprogramacion2.Model.ProductComponent;
import org.example.proyectofinalprogramacion2.Model.PurchaseStatus;
import org.example.proyectofinalprogramacion2.Model.Ticket;
import org.example.proyectofinalprogramacion2.Patterns.Creational.Purchase;
import org.example.proyectofinalprogramacion2.Services.DataStore;

public class ReportService {
    public String generateAndExport(String type, String path) {
        double totalVentas = 0;
        double ingresosBoletos = 0;
        double ingresosConfiteria = 0;
        int totalCompras = 0;
        int ticketsVendidos = 0;

        // Calcular datos reales
        for (Purchase p : DataStore.allPurchases) {
            if (p.getStatus() == PurchaseStatus.PAID) {
                totalVentas += p.getTotal();
                totalCompras++;
                ticketsVendidos += p.getTickets().size();

                for (Ticket t : p.getTickets()) {
                    ingresosBoletos += t.getPrice();
                }
                for (ProductComponent item : p.getItems()) {
                    ingresosConfiteria += item.getFinalPrice();
                }
            }
        }

        // Formatear el reporte
        String content = String.format(
                "=== CinemaUQ - REPORTE DE VENTAS (%s) ===\n\n" +
                        "Total de Compras Exitosas: %d\n" +
                        "Total de Boletos Vendidos: %d\n\n" +
                        "--- DESGLOSE DE INGRESOS ---\n" +
                        "Ingresos por Boletos: $%,.0f\n" +
                        "Ingresos por Confitería: $%,.0f\n\n" +
                        "TOTAL FACTURADO (con IVA): $%,.0f\n\n" +
                        "Generado el: %s\n",
                type.toUpperCase(), totalCompras, ticketsVendidos,
                ingresosBoletos, ingresosConfiteria, totalVentas,
                java.time.LocalDateTime.now()
        );

        // Exportar (Lógica de los patrones que ya tienes)
        if (type.equalsIgnoreCase("TXT")) {
            System.out.println("Exportando TXT a: " + path);
        } else {
            System.out.println("Exportando PDF a: " + path);
        }

        return content;
    }
}