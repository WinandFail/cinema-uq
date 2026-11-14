// ExternalPdfLibrary.java
package org.example.proyectofinalprogramacion2.Patterns.Structural;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ExternalPdfLibrary {
    public void createPdfDocument(String text, String path) {
        try {
            File file = new File(path);
            if (file.getParentFile() != null) file.getParentFile().mkdirs();
            String[] lines = text.split("\n");
            StringBuilder contentStream = new StringBuilder("BT /F1 11 Tf 50 750 Td 14 TL ");
            for (String line : lines) {
                String safe = line.replace("(", "\\(").replace(")", "\\)").replaceAll("[^\\x20-\\x7E]", " ");
                contentStream.append("(").append(safe).append(") Tj T* ");
            }
            contentStream.append("ET");
            byte[] streamBytes = contentStream.toString().getBytes(StandardCharsets.US_ASCII);

            String pdf = "%PDF-1.4\n" +
                    "1 0 obj << /Type /Catalog /Pages 2 0 R >> endobj\n" +
                    "2 0 obj << /Type /Pages /Kids [3 0 R] /Count 1 >> endobj\n" +
                    "3 0 obj << /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] /Contents 4 0 R /Resources << /Font << /F1 5 0 R >> >> >> endobj\n" +
                    "4 0 obj << /Length " + streamBytes.length + " >> stream\n" + contentStream + "\nendstream endobj\n" +
                    "5 0 obj << /Type /Font /Subtype /Type1 /BaseFont /Helvetica >> endobj\n" +
                    "xref\n0 6\n0000000000 65535 f \n0000000009 00000 n \n0000000058 00000 n \n0000000115 00000 n \n0000000241 00000 n \n0000000350 00000 n \n" +
                    "trailer << /Size 6 /Root 1 0 R >>\nstartxref\n420\n%%EOF";

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(pdf.getBytes(StandardCharsets.US_ASCII));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error generando PDF: " + e.getMessage(), e);
        }
    }
}