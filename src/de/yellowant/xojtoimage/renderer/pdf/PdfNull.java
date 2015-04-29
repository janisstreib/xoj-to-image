package de.yellowant.xojtoimage.renderer.pdf;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Anton Schirg
 */
public class PdfNull extends PdfObject {
	@Override
	public void render(OutputStreamWriter out) throws IOException {
		out.write("null");
	}
}
