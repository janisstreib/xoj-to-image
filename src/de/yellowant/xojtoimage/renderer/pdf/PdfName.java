package de.yellowant.xojtoimage.renderer.pdf;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Anton Schirg
 */
public class PdfName extends PdfObject {
	public String name;

	public PdfName(String name) {
		this.name = name;
	}

	@Override
	public void render(OutputStreamWriter out) throws IOException {
		out.write(getName());
	}

	public String getName() {
		return "/" + name;
	}
}
