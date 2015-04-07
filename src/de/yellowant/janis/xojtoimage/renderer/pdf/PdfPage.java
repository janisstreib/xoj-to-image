package de.yellowant.janis.xojtoimage.renderer.pdf;

/**
 * @author Anton Schirg
 */
public class PdfPage extends PdfObject{
    PdfIndirectObject page;
    PdfIndirectObject contents;
    private final PdfStream contentsStream;

    public PdfPage(PdfDocument document) {
        PdfDictionary pageDict = new PdfDictionary();
        pageDict.dict.put(new PdfName("Type"), new PdfName("Page"));
        pageDict.dict.put(new PdfName("Parent"), new PdfIndirectReference(document.pagesObject));
//        PdfDictionary resDict = new PdfDictionary();
//        PdfDictionary fontDict = new PdfDictionary();
//        fontDict.dict.put(new PdfName("F1"), new PdfIndirectReference(fontDefObject));
//        resDict.dict.put(new PdfName("Font"), fontDict);
//        pageDict.dict.put(new PdfName("Resources"), resDict);
        PdfArray mediaBoxArray = new PdfArray();
        mediaBoxArray.elements.add(new PdfInteger(0));
        mediaBoxArray.elements.add(new PdfInteger(0));
        mediaBoxArray.elements.add(new PdfInteger(612));
        mediaBoxArray.elements.add(new PdfInteger(792));
        pageDict.dict.put(new PdfName("MediaBox"), mediaBoxArray);
        page = document.constructIndirectObject(pageDict);

        contentsStream = new PdfStream();
        contents = document.constructIndirectObject(contentsStream);

        pageDict.dict.put(new PdfName("Contents"), new PdfIndirectReference(contents));
    }

    void drawLine(double x1, double y1, double x2, double y2) {
        contentsStream.content.append(x1).append(" ").append(y1).append(" m ").append(x2).append(" ").append(y2).append(" l S\n");
    }

    void drawStroke(double[] x, double[] y) {
        if (x.length != y.length)
            throw new IllegalArgumentException("x and y have to be of same length");
        if (x.length < 2)
            throw new IllegalArgumentException("stroke has to have at least two points");
        contentsStream.content.append(x[0]).append(" ").append(y[0]).append(" m");
        for (int i = 0; i < x.length; i++) {
            contentsStream.content.append(" ").append(x[i]).append(" ").append(y[i]).append(" l");
        }
        contentsStream.content.append(" S");
    }

    @Override
    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append(page.render()).append("\n").append(contents.render());
        return sb.toString();
    }
}
