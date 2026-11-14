
package org.example.proyectofinalprogramacion2.Report;

import org.example.proyectofinalprogramacion2.Model.*;
import org.example.proyectofinalprogramacion2.Patterns.Creational.*;
import org.example.proyectofinalprogramacion2.Patterns.Structural.PdfReportAdapter;
import org.example.proyectofinalprogramacion2.Services.DataStore;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportService {
    public String generateAndExport(String type, String path) {
        double totalVentas = 0, ingresosBoletos = 0, ingresosConfiteria = 0;
        int totalCompras = 0, ticketsVendidos = 0, comprasCanceladas = 0;

        for (Purchase p : DataStore.allPurchases) {
            if (p.getStatus() == PurchaseStatus.PAID) {
                totalVentas += p.getTotal();
                totalCompras++;
                ticketsVendidos += p.getTickets().size();
                for (Ticket t : p.getTickets()) ingresosBoletos += t.getPrice();
                for (ProductComponent item : p.getItems()) ingresosConfiteria += item.getFinalPrice();
            } else if (p.getStatus() == PurchaseStatus.CANCELLED || p.getStatus() == PurchaseStatus.REFUNDED) {
                comprasCanceladas++;
            }
        }

        Report baseReport = ReportFactory.create(type);
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        String content = String.format(
                "%s\n" +
                        "Fecha de generacion: %s\n" +
                        "Periodo consultado: Operacion Actual CinemaUQ\n" +
                        "--------------------------------------------------\n" +
                        "Compras Exitosas: %d | Compras Canceladas: %d\n" +
                        "Boletos Vendidos: %d\n" +
                        "Ingresos Boletos: $%,.0f\n" +
                        "Ingresos Confiteria: $%,.0f\n" +
                        "TOTAL FACTURADO (con IVA 19%%): $%,.0f\n",
                baseReport.generate().split("\n")[0], fecha,
                totalCompras, comprasCanceladas, ticketsVendidos,
                ingresosBoletos, ingresosConfiteria, totalVentas
        );

        try {
            File file = new File(path);
            if (file.getParentFile() != null) file.getParentFile().mkdirs();
            if (type.equalsIgnoreCase("TXT")) {
                Files.writeString(Path.of(path), content);
            } else {
                ReportGenerator pdfAdapter = new PdfReportAdapter();
                pdfAdapter.generate(content, path);
            }
        } catch (Exception e) {
            throw new RuntimeException("No se pudo exportar el reporte: " + e.getMessage());
        }
        return content;
    }
}