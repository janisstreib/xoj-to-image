package de.yellowant.xojtoimage.renderer.pdf;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Anton Schirg
 */
public class PdfIndirectReference extends PdfObject {
	private final int number;
	private final int generation;

	public PdfIndirectReference(int number, int generation) {
		this.number = number;
		this.generation = generation;
	}

	public PdfIndirectReference(PdfIndirectObject referenceTo) {
		this.number = referenceTo.number;
		this.generation = referenceTo.generation;
	}

	@Override
	public void render(OutputStreamWriter out) throws IOException {
		out.write(number + " " + generation + " R");
	}
}
