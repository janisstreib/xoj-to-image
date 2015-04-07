package de.yellowant.xojtoimage.renderer.pdf;

/**
 * @author Anton Schirg
 */
public class PdfIndirectObject extends PdfObject { //TODO Make generic for contained object type
    public PdfObject object;
    public int number;
    public int generation;

    public PdfIndirectObject(PdfObject object, int number, int generation) {
        this.object = object;
        this.number = number;
        this.generation = generation;
    }

    @Override
    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append(number).append(" ").append(generation).append(" obj\n");
        sb.append(object.render());
        sb.append("\nendobj");
        return sb.toString();
    }
}