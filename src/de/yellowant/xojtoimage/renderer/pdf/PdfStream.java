package de.yellowant.xojtoimage.renderer.pdf;

/**
 * @author Anton Schirg
 */
public class PdfStream extends PdfObject {
    StringBuffer content;

    public PdfStream() {
        this.content = new StringBuffer();
    }

    @Override
    public String render() {
        PdfDictionary streamDict = new PdfDictionary();
        streamDict.dict.put(new PdfName("Length"), new PdfLong(content.length()));
        StringBuilder sb = new StringBuilder();
        sb.append(streamDict.render()).append("\n");
        sb.append("stream\n");
        sb.append(content).append("\n");
        sb.append("endstream");
        return sb.toString();
    }
}
