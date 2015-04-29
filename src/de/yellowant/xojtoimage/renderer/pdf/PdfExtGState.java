package de.yellowant.xojtoimage.renderer.pdf;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anton Schirg
 */
public class PdfExtGState extends PdfObject {

	public Map<Integer, PdfName> alphas; // Alpha in percent
	int alphaNameCounter = 0;

	public PdfExtGState() {
		alphas = new HashMap<>();
	}

	public PdfName getAlpha(double opacity) {
		int alpha = (int) (opacity * 100);
		PdfName alphaName = alphas.get(alpha);
		if (alphaName != null) {
			return alphaName;
		} else {
			alphaName = new PdfName("a" + alphaNameCounter);
			alphaNameCounter++;
			alphas.put(alpha, alphaName);
			return alphaName;
		}
	}

	@Override
	public void render(OutputStreamWriter out) throws IOException {
		PdfDictionary gstate = new PdfDictionary();
		for (Map.Entry<Integer, PdfName> alphaEntry : alphas.entrySet()) {
			PdfDictionary innerDict = new PdfDictionary();
			innerDict.dict.put(new PdfName("CA"),
					new PdfReal(alphaEntry.getKey() / 100.0));
			gstate.dict.put(alphaEntry.getValue(), innerDict);
		}
		gstate.render(out);
	}
}
