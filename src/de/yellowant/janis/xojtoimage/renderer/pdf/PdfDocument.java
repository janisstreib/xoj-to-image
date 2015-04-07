package de.yellowant.janis.xojtoimage.renderer.pdf;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Anton Schirg
 */
public class PdfDocument {
    public List<PdfIndirectObject> objects = new LinkedList<>();
    public PdfIndirectObject rootObject;
    int objectCounter = 0;

    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("%PDF-1.0\n\n");

        for (PdfObject object : objects) {
            sb.append(object.render());
            sb.append("\n\n");
        }

        sb.append("xref\n");
        sb.append("0 ").append(objects.size() + 1).append("\n\n"); //Num object incl xref

        sb.append("trailer\n");
        PdfDictionary trailerDict = new PdfDictionary();
        trailerDict.dict.put(new PdfName("Size"), new PdfInteger(objects.size() + 1));
        trailerDict.dict.put(new PdfName("Root"), new PdfIndirectReference(rootObject));
        sb.append(trailerDict.render()).append("\n");
        sb.append("startxref\n\n");

        sb.append("%%EOF");
        return sb.toString();
    }

    public PdfIndirectObject addAsIndirectObject(PdfObject object) {
        objectCounter++;
        PdfIndirectObject indirectObject = new PdfIndirectObject(object, objectCounter, 0);
        objects.add(indirectObject);
        return indirectObject;
    }
}
