package de.yellowant.xojtoimage.renderer.pdf;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Anton Schirg
 */
public class PdfStream extends PdfObject {
	StringBuffer content;

	public PdfStream() {
		this.content = new StringBuffer();
	}

	@Override
	public void render(OutputStreamWriter out) throws IOException {
		PdfDictionary streamDict = new PdfDictionary();
		streamDict.dict.put(new PdfName("Length"),
				new PdfLong(content.length()));
		streamDict.render(out);
		out.write("\n");
		out.write("stream\n");
		out.write(content.toString());
		out.write("\n");
		out.write("endstream");
	}
}
