package de.yellowant.janis.xojtoimage.renderer.pdf;

/**
 * @author Anton Schirg
 */
public class PdfReal extends PdfObject {
    double real;

    public PdfReal(double real) {
        this.real = real;
    }

    @Override
    public String render() {
        return Double.toString(real);
    }
}
