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
        PdfIndirectObject rootObject = doc.addAsIndirectObject(catalogDict);

        PdfDictionary fontDefDict = new PdfDictionary();
        fontDefDict.dict.put(new PdfName("Type"), new PdfName("Font"));
        fontDefDict.dict.put(new PdfName("Subtype"), new PdfName("Type1"));
        fontDefDict.dict.put(new PdfName("BaseFont"), new PdfName("Helvetica"));
        PdfIndirectObject fontDefObject = doc.addAsIndirectObject(fontDefDict);

        PdfDictionary pagesDict = new PdfDictionary();
        pagesDict.dict.put(new PdfName("Type"), new PdfName("Pages"));
        pagesDict.dict.put(new PdfName("Count"), new PdfInteger(1));
        PdfArray pagesArray = new PdfArray();
        pagesDict.dict.put(new PdfName("Kids"), pagesArray);
        PdfIndirectObject pagesObject = doc.addAsIndirectObject(pagesDict);
        catalogDict.dict.put(new PdfName("Pages"), new PdfIndirectReference(pagesObject));

        PdfDictionary pageDict = new PdfDictionary();
        pageDict.dict.put(new PdfName("Type"), new PdfName("Page"));
        pageDict.dict.put(new PdfName("Parent"), new PdfIndirectReference(pagesObject));
        PdfDictionary resDict = new PdfDictionary();
        PdfDictionary fontDict = new PdfDictionary();
        fontDict.dict.put(new PdfName("F1"), new PdfIndirectReference(fontDefObject));
        resDict.dict.put(new PdfName("Font"), fontDict);
        pageDict.dict.put(new PdfName("Resources"), resDict);
        PdfArray mediaBoxArray = new PdfArray();
        mediaBoxArray.elements.add(new PdfInteger(0));
        mediaBoxArray.elements.add(new PdfInteger(0));
        mediaBoxArray.elements.add(new PdfInteger(612));
        mediaBoxArray.elements.add(new PdfInteger(792));
        pageDict.dict.put(new PdfName("MediaBox"), mediaBoxArray);
        PdfIndirectObject pageObject = doc.addAsIndirectObject(pageDict);
        pagesArray.elements.add(new PdfIndirectReference(pageObject));

        String contents = "BT\n" +
                "/F1 24 Tf\n" +
                "250 700 Td (Hello, World!) Tj\n" +
                "ET\n" +
                "0 10 m 100 200 l h S";
        PdfStream contentsStream = new PdfStream(contents);
        PdfIndirectObject contentsObject = doc.addAsIndirectObject(contentsStream);
        pageDict.dict.put(new PdfName("Contents"), new PdfIndirectReference(contentsObject));


        doc.rootObject = rootObject;
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
