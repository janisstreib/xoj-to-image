package de.yellowant.xojtoimage.renderer.pdf;

/**
 * @author Anton Schirg
 */
public class PdfIndirectReference extends PdfObject {
    private final int number;
    private final int generation;

    public PdfIndirectReference(int number, int generation) {
        this.number = number;
        this.generation = generation;
    }

    public PdfIndirectReference(PdfIndirectObject referenceTo) {
        this.number = referenceTo.number;
        this.generation = referenceTo.generation;
    }

    @Override
    public String render() {
        return number + " " + generation + " R";
    }
}
