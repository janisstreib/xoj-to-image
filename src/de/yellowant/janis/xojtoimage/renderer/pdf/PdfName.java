package de.yellowant.janis.xojtoimage.renderer.pdf;

/**
 * @author Anton Schirg
 */
public class PdfName extends PdfObject {
    public String name;

    public PdfName(String name) {
        this.name = name;
    }

    @Override
    public String render() {
        return "/" + name;
    }
}
