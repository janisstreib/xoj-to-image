package de.yellowant.xojtoimage.renderer.pdf;

/**
 * @author Anton Schirg
 */
public class PdfInteger extends PdfObject {
    public Integer value;

    public PdfInteger(Integer value) {
        this.value = value;
    }

    @Override
    public String render() {
        return value.toString();
    }
}
