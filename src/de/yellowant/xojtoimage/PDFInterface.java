package de.yellowant.xojtoimage;

import de.yellowant.xojtoimage.renderer.pdf.PdfRenderer;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;

public class PDFInterface {
	private static final String USAGE = "Uasge:\njava -jar xojtoimage.jar <Xournal file> <ouput file>";

	public static void main(String[] args) {
		try {
			XOJToImage xoj = new XOJToImage(new File(args[0]), new PdfRenderer());
			xoj.export(args[1], "", 0);
			System.out.println("Done. Processed "
                    + xoj.getXournal().getPages().size() + " pages.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}
}
