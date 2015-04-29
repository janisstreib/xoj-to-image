package de.yellowant.xojtoimage.renderer.pdf;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Anton Schirg
 */
public class PdfArray extends PdfObject {
	public List<PdfObject> elements = new LinkedList<>();

	@Override
	public void render(OutputStreamWriter out) throws IOException {
		out.write("[");
		String delim = "";
		for (PdfObject element : elements) {
			out.write(delim);
			element.render(out);
			delim = " ";
		}
		out.write("]");
	}
}
