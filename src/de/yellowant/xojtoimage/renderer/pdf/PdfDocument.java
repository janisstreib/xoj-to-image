package de.yellowant.xojtoimage.renderer.pdf;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Anton Schirg
 */
public class PdfDocument {
    public List<PdfPage> pages = new LinkedList<>();
    public List<PdfIndirectObject> fonts = new LinkedList<>();
    final PdfIndirectObject rootObject;

    final PdfIndirectObject pagesObject;
    final PdfArray pagesArray;
    private int objectCounter = 0;
    private final PdfInteger pageCount;

    public PdfDocument() {
        PdfDictionary catalogDict = new PdfDictionary();
        catalogDict.dict.put(new PdfName("Type"), new PdfName("Catalog"));
        rootObject = constructIndirectObject(catalogDict);

        PdfDictionary pagesDict = new PdfDictionary();
        pagesDict.dict.put(new PdfName("Type"), new PdfName("Pages"));
        pagesArray = new PdfArray();
        pagesDict.dict.put(new PdfName("Kids"), pagesArray);
        pagesObject = constructIndirectObject(pagesDict);
        pageCount = new PdfInteger(pagesArray.elements.size());
        pagesDict.dict.put(new PdfName("Count"), pageCount);
        catalogDict.dict.put(new PdfName("Pages"), new PdfIndirectReference(pagesObject));
    }

    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("%PDF-1.0\n\n");

        sb.append(rootObject.render()).append("\n\n");

        sb.append(pagesObject.render()).append("\n\n");

        for (PdfIndirectObject font : fonts) {
            sb.append(font.render()).append("\n");
        }
        sb.append("\n");

        for (PdfPage page : pages) {
            sb.append(page.render()).append("\n");
        }
        sb.append("\n");

        sb.append("xref\n");
        sb.append("0 ").append(objectCounter + 1).append("\n\n"); //Num object incl xref

        sb.append("trailer\n");
        PdfDictionary trailerDict = new PdfDictionary();
        trailerDict.dict.put(new PdfName("Size"), new PdfInteger(objectCounter + 1));
        trailerDict.dict.put(new PdfName("Root"), new PdfIndirectReference(rootObject));
        sb.append(trailerDict.render()).append("\n");
        sb.append("startxref\n\n");

        sb.append("%%EOF");
        return sb.toString();
    }

    public PdfIndirectObject constructIndirectObject(PdfObject object) {
        objectCounter++;
        return new PdfIndirectObject(object, objectCounter, 0);
    }

    public PdfPage addPage(double width, double height) {
        PdfPage page = new PdfPage(this,width, height);
        pages.add(page);
        pagesArray.elements.add(new PdfIndirectReference(page.page));
        pageCount.value = pagesArray.elements.size();
        return page;
    }

    public PdfIndirectObject addFont(String baseFont) {
        PdfDictionary fontDefDict = new PdfDictionary();
        fontDefDict.dict.put(new PdfName("Type"), new PdfName("Font"));
        fontDefDict.dict.put(new PdfName("Subtype"), new PdfName("Type1"));
        fontDefDict.dict.put(new PdfName("BaseFont"), new PdfName(baseFont));
        PdfIndirectObject indirectObject = constructIndirectObject(fontDefDict);
        fonts.add(indirectObject);
        return indirectObject;
    }
}
