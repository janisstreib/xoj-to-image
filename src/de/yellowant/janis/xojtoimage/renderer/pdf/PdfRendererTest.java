package de.yellowant.janis.xojtoimage.renderer.pdf;

import org.junit.Test;

public class PdfRendererTest {

    @Test
    public void testWriteToFile() throws Exception {
        PdfRenderer renderer = new PdfRenderer();
        renderer.render();
        renderer.writeToFile("test.pdf");
    }
}