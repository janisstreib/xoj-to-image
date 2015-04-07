package de.yellowant.xojtoimage.renderer.pdf;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Anton Schirg
 */
public class PdfArray extends PdfObject {
    public List<PdfObject> elements = new LinkedList<>();

    @Override
    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        String delim = "";
        for (PdfObject element : elements) {
            sb.append(delim).append(element.render());
            delim = " ";
        }
        sb.append("]");
        return sb.toString();
    }
}
