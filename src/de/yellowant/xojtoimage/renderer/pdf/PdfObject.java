package de.yellowant.xojtoimage.renderer.pdf;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author Anton Schirg
 */
public abstract class PdfObject {
	public abstract void render(OutputStreamWriter out) throws IOException;
}
