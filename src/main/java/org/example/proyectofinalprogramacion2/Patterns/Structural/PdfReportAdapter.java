package org.example.proyectofinalprogramacion2.Patterns.Structural;

import org.example.proyectofinalprogramacion2.Report.ReportGenerator;

public class PdfReportAdapter implements ReportGenerator {
    private ExternalPdfLibrary externalLib = new ExternalPdfLibrary();

    @Override
    public void generate(String content, String filePath) {
        externalLib.createPdfDocument(content, filePath);
    }
}
