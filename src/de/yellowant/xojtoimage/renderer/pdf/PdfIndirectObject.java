package de.yellowant.xojtoimage.renderer.pdf;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Anton Schirg
 */
public class PdfIndirectObject extends PdfObject { // TODO Make generic for
													// contained object type
	public PdfObject object;
	public int number;
	public int generation;

	public PdfIndirectObject(PdfObject object, int number, int generation) {
		this.object = object;
		this.number = number;
		this.generation = generation;
	}

	@Override
	public void render(OutputStreamWriter out) throws IOException {
		out.write(number + "");
		out.write(" ");
		out.write(generation + "");
		out.write(" obj\n");
		object.render(out);
		out.write("\nendobj");
	}
}
