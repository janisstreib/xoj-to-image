package de.yellowant.xojtoimage.renderer.pdf;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Anton Schirg
 */
public class PdfReal extends PdfObject {
	double real;

	public PdfReal(double real) {
		this.real = real;
	}

	@Override
	public void render(OutputStreamWriter out) throws IOException {
		out.write(real + "");
	}
}
