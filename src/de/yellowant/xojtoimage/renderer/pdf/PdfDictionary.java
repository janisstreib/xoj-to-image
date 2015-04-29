package de.yellowant.xojtoimage.renderer.pdf;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anton Schirg
 */
public class PdfDictionary extends PdfObject {
	public Map<PdfName, PdfObject> dict = new HashMap<>();

	@Override
	public void render(OutputStreamWriter out) throws IOException {
		out.write("<< ");
		for (Map.Entry<PdfName, PdfObject> pdfObjectEntry : dict.entrySet()) {
			pdfObjectEntry.getKey().render(out);
			out.write(" ");
			pdfObjectEntry.getValue().render(out);
			out.write("\n");
		}
		out.write(">>");
	}
}
