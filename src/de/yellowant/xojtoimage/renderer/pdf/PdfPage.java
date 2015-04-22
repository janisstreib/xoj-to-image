package de.yellowant.xojtoimage.renderer.pdf;

import java.awt.*;

/**
 * @author Anton Schirg
 */
public class PdfPage extends PdfObject{
    final PdfIndirectObject page;
    final PdfIndirectObject contents;
    private final PdfStream contentsStream;
    final PdfIndirectObject resources;
    private final PdfExtGState extGState;

    public PdfPage(PdfDocument document, double width, double height) {
        PdfDictionary pageDict = new PdfDictionary();
        pageDict.dict.put(new PdfName("Type"), new PdfName("Page"));
        pageDict.dict.put(new PdfName("Parent"), new PdfIndirectReference(document.pagesObject));
//        PdfDictionary resDict = new PdfDictionary();
//        PdfDictionary fontDict = new PdfDictionary();
//        fontDict.dict.put(new PdfName("F1"), new PdfIndirectReference(fontDefObject));
//        resDict.dict.put(new PdfName("Font"), fontDict);
//        pageDict.dict.put(new PdfName("Resources"), resDict);
        PdfArray mediaBoxArray = new PdfArray();
        mediaBoxArray.elements.add(new PdfLong(0));
        mediaBoxArray.elements.add(new PdfLong(0));
        mediaBoxArray.elements.add(new PdfReal(width));
        mediaBoxArray.elements.add(new PdfReal(height));
        pageDict.dict.put(new PdfName("MediaBox"), mediaBoxArray);
        page = document.constructIndirectObject(pageDict);

        contentsStream = new PdfStream();
        contents = document.constructIndirectObject(contentsStream);

        PdfDictionary pageResources = new PdfDictionary();
        extGState = new PdfExtGState();
        pageResources.dict.put(new PdfName("ExtGState"), extGState);
        resources = document.constructIndirectObject(pageResources);

        pageDict.dict.put(new PdfName("Resources"), new PdfIndirectReference(resources));
        pageDict.dict.put(new PdfName("Contents"), new PdfIndirectReference(contents));

        setLineCap(1);
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
        for (int i = 1; i < x.length; i++) {
            contentsStream.content.append(" ").append(x[i]).append(" ").append(y[i]).append(" l");
        }
        contentsStream.content.append(" S\n");
    }

    void drawStrokeVaryingWidth(double[] x, double[] y, double[] widths) { //TODO doesn't look beautiful
        if (x.length != y.length)
            throw new IllegalArgumentException("x and y have to be of same length");
        if (x.length < 2)
            throw new IllegalArgumentException("stroke has to have at least two points");

        for (int i = 1; i < x.length; i++) {
            if (i - 1 < widths.length)
                setStrokeWidth(widths[i-1]);
            drawLine(x[i-1], y[i-1], x[i], y[i]);
        }
    }

    void setStrokeWidth(double width) {
        contentsStream.content.append(width).append(" w\n");
    }

    void setStrokeColor(Color color){
        contentsStream.content.append(color.getRed() / 255.0).append(" ")
                .append(color.getGreen() / 255.0).append(" ")
                .append(color.getBlue() / 255.0).append(" RG\n");

    }

    void setAlpha(double alpha) {
        PdfName alphaName = extGState.getAlpha(alpha);
        contentsStream.content.append(alphaName.render()).append(" gs\n");
    }

    void setLineCap(int capStyle) {
        contentsStream.content.append(capStyle).append(" J\n");
    }

    @Override
    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append(page.render()).append("\n").append(resources.render()).append("\n").append(contents.render());
        return sb.toString();
    }
}
