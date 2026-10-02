package org.example.proyectofinalprogramacion2.Report;

import org.example.proyectofinalprogramacion2.Patterns.Creational.Report;
import org.example.proyectofinalprogramacion2.Patterns.Creational.ReportFactory;

public class ReportService {
    public String generateAndExport(String type, String path) {
        Report r = ReportFactory.create(type);
        String content = r.generate();
        r.export(path);
        return content;
    }
}