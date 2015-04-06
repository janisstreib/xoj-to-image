package de.yellowant.janis.xojtopdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XOJToPDF {
	private GZIPInputStream input;
	private Xournal xournal;
	private static final float FACTOR = 2;

	public static void main(String[] args) throws FileNotFoundException,
			IOException, XmlPullParserException {
		XOJToPDF xoj = new XOJToPDF(new File("test.xoj"));
		Xournal xour = xoj.getXournal();
		int pageNumber = 1;
		for (Page p : xour.getPages()) {
			final PageCanvas comp = new PageCanvas(
					(int) (p.getHeight() * FACTOR),
					(int) (p.getWidth() * FACTOR));
			for (Layer l : p.getLayers()) {
				for (Stroke stroke : l.getStrokes()) {
					double[] coords = stroke.getCoords();
					double[] widths = stroke.getWidths();
					for (int i = 0; i < coords.length - 2; i += 2) {
						double width;
						if (widths.length == 0) {
							width = 1;
						} else {
							if (i > widths.length - 1) {
								width = widths[widths.length - 1];
							} else {
								width = widths[i];
								if (i != 0) {
									width = widths[i - 1];
								}
							}
						}
						comp.addLine(coords[i] * FACTOR,
								coords[i + 1] * FACTOR, coords[i + 2] * FACTOR,
								coords[i + 3] * FACTOR, Math.max(width, 1)
										* FACTOR);
					}
				}
			}
			comp.exportImage("test_" + pageNumber++ + ".png");
		}
	}

	public XOJToPDF(File input) throws FileNotFoundException, IOException,
			XmlPullParserException {
		this.input = new GZIPInputStream(new FileInputStream(input));
		// byte[] buf = new byte[512];
		// FileOutputStream out = new FileOutputStream(new File("test.xml"));
		// int read;
		// while ((read = this.input.read(buf)) != -1) {
		// out.write(buf, 0, read);
		// }
		// out.close();
		XmlPullParser parser = XmlPullParserFactory.newInstance()
				.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(new InputStreamReader(this.input));
		parser.nextTag();
		parser.require(XmlPullParser.START_TAG, null, "xournal");
		xournal = new Xournal(parser);
		this.input.close();
	}

	public Xournal getXournal() {
		return xournal;
	}

}
