package de.yellowant.xojtoimage.renderer.pdf;

/**
 * @author Anton Schirg
 */
public class PdfNull extends PdfObject {
    @Override
    public String render() {
        return "null";
    }
}
