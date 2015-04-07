package de.yellowant.xojtoimage.renderer.pdf;

import de.yellowant.xojtoimage.renderer.Renderer;
import de.yellowant.xojtoimage.xournalelements.Layer;
import de.yellowant.xojtoimage.xournalelements.Page;
import de.yellowant.xojtoimage.xournalelements.Stroke;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

/**
 * @author Anton Schirg
 */
public class PdfRenderer implements Renderer{
    PdfDocument doc;
    String rendered;
    private LinkedList<Page> xojPages;

    @Override
    public void export(String name, String format, int pageIndex) throws IOException {
        doc = new PdfDocument();
        for (Page xojPage : xojPages) {
            PdfPage pdfPage = doc.addPage();
            for (Layer layer : xojPage.getLayers()) {
                for (Stroke stroke : layer.getStrokes()) {
                    if (stroke.getWidths().length == 1) {
                        pdfPage.setStrokeWidth(stroke.getWidths()[0]);
                        pdfPage.drawStroke(getEven(stroke.getCoords()), getOdd(stroke.getCoords()));
                    } else {
                        pdfPage.drawStrokeVaryingWidth(getEven(stroke.getCoords()), getOdd(stroke.getCoords()), stroke.getWidths());
                    }
                }
            }
        }

        rendered = doc.render();
        writeToFile(name);
    }

    private double[] getEven(double[] array) {
        int num = (int)Math.ceil(array.length/2.0);
        double[] result = new double[num];
        for (int i = 0; i < num; i++) {
            result[i] = array[i*2];
        }
        return result;
    }

    private double[] getOdd(double[] array) {
        int num = (int)Math.floor(array.length/2.0);
        double[] result = new double[num];
        for (int i = 0; i < num; i++) {
            result[i] = array[i*2+1];
        }
        return result;
    }

    public void writeToFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename)));
        writer.write(rendered);
        writer.close();
    }

    @Override
    public void setPages(LinkedList<Page> pages) {
        this.xojPages = pages;
    }
}
