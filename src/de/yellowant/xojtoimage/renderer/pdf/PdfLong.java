package de.yellowant.xojtoimage.renderer.pdf;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Anton Schirg
 */
public class PdfLong extends PdfObject {
	public long value;

	public PdfLong(long value) {
		this.value = value;
	}

	@Override
	public void render(OutputStreamWriter out) throws IOException {
		out.write(value + "");
	}
}
