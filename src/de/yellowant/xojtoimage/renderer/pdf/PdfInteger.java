package de.yellowant.xojtoimage.renderer.pdf;

/**
 * @author Anton Schirg
 */
public class PdfInteger extends PdfObject {
    private int value;

    public PdfInteger(int value) {
        this.value = value;
    }

    @Override
    public String render() {
        return Integer.toString(value);
    }
}
