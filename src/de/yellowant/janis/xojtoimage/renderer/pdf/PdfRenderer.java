package de.yellowant.janis.xojtoimage.renderer.pdf;

import java.io.*;

/**
 * @author Anton Schirg
 */
public class PdfRenderer {
    PdfDocument doc = new PdfDocument();
    String rendered;

    public PdfRenderer() {
        PdfDictionary catalogDict = new PdfDictionary();
        catalogDict.dict.put(new PdfName("Type"), new PdfName("Catalog"));
        catalogDict.dict.put(new PdfName("Pages"), new PdfIndirectReference(2, 0));
        doc.objects.add(new PdfIndirectObject(catalogDict, 1, 0));

        PdfDictionary pagesDict = new PdfDictionary();
        pagesDict.dict.put(new PdfName("Type"), new PdfName("Pages"));
        pagesDict.dict.put(new PdfName("Count"), new PdfInteger(1));
        PdfArray pagesArray = new PdfArray();
        pagesArray.elements.add(new PdfIndirectReference(3, 0));
        pagesDict.dict.put(new PdfName("Kids"), pagesArray);
        doc.objects.add(new PdfIndirectObject(pagesDict, 2, 0));

        PdfDictionary pageDict = new PdfDictionary();
        pageDict.dict.put(new PdfName("Type"), new PdfName("Page"));
        pageDict.dict.put(new PdfName("Parent"), new PdfIndirectReference(2, 0));
        PdfDictionary resDict = new PdfDictionary();
        PdfDictionary fontDict = new PdfDictionary();
        fontDict.dict.put(new PdfName("F1"), new PdfIndirectReference(5, 0));
        resDict.dict.put(new PdfName("Font"), fontDict);
        pageDict.dict.put(new PdfName("Resources"), resDict);
        PdfArray mediaBoxArray = new PdfArray();
        mediaBoxArray.elements.add(new PdfInteger(0));
        mediaBoxArray.elements.add(new PdfInteger(0));
        mediaBoxArray.elements.add(new PdfInteger(612));
        mediaBoxArray.elements.add(new PdfInteger(792));
        pageDict.dict.put(new PdfName("MediaBox"), mediaBoxArray);
        pageDict.dict.put(new PdfName("Contents"), new PdfIndirectReference(4, 0));
        doc.objects.add(new PdfIndirectObject(pageDict, 3, 0));

        String contents = "BT\n" +
                "/F1 24 Tf\n" +
                "250 700 Td (Hello, World!) Tj\n" +
                "ET";
        PdfStream contentsStream = new PdfStream(contents);
        doc.objects.add(new PdfIndirectObject(contentsStream, 4, 0));

        PdfDictionary fontDefDict = new PdfDictionary();
        fontDefDict.dict.put(new PdfName("Type"), new PdfName("Font"));
        fontDefDict.dict.put(new PdfName("Subtype"), new PdfName("Type1"));
        fontDefDict.dict.put(new PdfName("BaseFont"), new PdfName("Helvetica"));
        doc.objects.add(new PdfIndirectObject(fontDefDict, 5, 0));
    }

    public void render() {
        rendered = doc.render();
    }



    public void writeToFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename)));
        writer.write(rendered);
        writer.close();
    }
}
