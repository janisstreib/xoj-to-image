package de.yellowant.xojtoimage.renderer.pdf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anton Schirg
 */
public class PdfDictionary extends PdfObject {
    public Map<PdfName, PdfObject> dict = new HashMap<>();

    @Override
    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("<< ");
        for (Map.Entry<PdfName, PdfObject> pdfObjectEntry : dict.entrySet()) {
            sb.append(pdfObjectEntry.getKey().render());
            sb.append(" ");
            sb.append(pdfObjectEntry.getValue().render());
            sb.append("\n");
        }
        sb.append(">>");
        return sb.toString();
    }
}
