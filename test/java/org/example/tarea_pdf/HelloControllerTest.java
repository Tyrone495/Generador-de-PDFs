package org.example.tarea_pdf;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HelloControllerTest {
    boolean existe;

    List<Cliente> clientes = new ArrayList<>();

    @Test
    void exportarAPDF() {
        Cliente cliente = new Cliente(4,"Ramiro","ramiro@gmail.com","Aldiba");
        Cliente cliente2 = new Cliente(7,"Pamela","pamela@gmail.com","Aldiba2");
        Cliente cliente3 = new Cliente(23,"Karen","karen@gmail.com","Aldiba3");
        Cliente cliente4 = new Cliente(12,"Cassandra","cassandra@gmail.com","Aldiba4");
        Cliente cliente5 = new Cliente(67,"Marisol","marisol@gmail.com","Aldiba5");
        Cliente cliente6 = new Cliente(07,"Lenin","lenin@gmail.com","Aldiba6");


        clientes.add(cliente);
        clientes.add(cliente2);
        clientes.add(cliente3);
        clientes.add(cliente4);
        clientes.add(cliente5);
        clientes.add(cliente6);
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);

            content.beginText();
            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
            content.newLineAtOffset(40, 750);

            content.showText("--- PRUEBA DATOS DEL CSV ---");
            content.showText("Contenido de clientes de prueba");
            content.newLineAtOffset(0, -20);

            for (Cliente c : clientes) {
                String info = c.getId() + " | " + c.getNombre() + " | " + c.getEmail() + " | " + c.getCiudad();
                content.showText(info);
                content.newLineAtOffset(0, -12);

            }
            content.endText();
            content.close();
            doc.save("prueba.pdf");
            File file = new File("prueba.pdf");
            if (file.exists()) {
                existe = true;
            } else existe = false;

            assertTrue(existe);

            // El Documento deberia de generarse en la Carpeta Raíz


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}