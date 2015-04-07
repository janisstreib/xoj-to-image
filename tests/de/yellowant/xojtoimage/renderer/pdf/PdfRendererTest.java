package de.yellowant.xojtoimage.renderer.pdf;

import org.junit.Test;

import de.yellowant.xojtoimage.renderer.pdf.PdfRenderer;

public class PdfRendererTest {

    @Test
    public void testWriteToFile() throws Exception {
        PdfRenderer renderer = new PdfRenderer();
        renderer.render();
        renderer.writeToFile("test.pdf");
    }
}