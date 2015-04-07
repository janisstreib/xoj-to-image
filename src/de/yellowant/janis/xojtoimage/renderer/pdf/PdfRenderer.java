package de.yellowant.janis.xojtoimage.renderer.pdf;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Anton Schirg
 */
public class PdfRenderer {
    PdfDocument doc;
    String rendered;

    public PdfRenderer() {
        doc = new PdfDocument();

        doc.addFont("Helvetica");

        PdfPage pdfPage = doc.addPage();
        pdfPage.drawLine(10, 20, 30, 40);
        pdfPage.drawStroke(new double[]{10, 20, 40, 80}, new double[]{10, 20, 30, 40});
    }

    public void render() {
        rendered = doc.render();
    }



    public void writeToFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename)));
        writer.write(rendered);
        writer.close();
    }
}
