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

import de.yellowant.janis.xojtopdf.xournalelements.Xournal;

public class XOJToImage {
	private Xournal xournal;
	private Renderer renderer;

	public XOJToImage(File input, Renderer renderer)
			throws FileNotFoundException, IOException, XmlPullParserException {
		this.renderer = renderer;
		GZIPInputStream inputStream = new GZIPInputStream(new FileInputStream(
				input));
		XmlPullParser parser = XmlPullParserFactory.newInstance()
				.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(new InputStreamReader(inputStream));
		parser.nextTag();
		parser.require(XmlPullParser.START_TAG, null, "xournal");
		xournal = new Xournal(parser);
		inputStream.close();
		renderer.setPages(xournal.getPages());
	}

	public Xournal getXournal() {
		return xournal;
	}

	public void export(String filename, String format, int page)
			throws IOException {
		renderer.export(filename, format, page);
	}

	public void exportAll(String filename, String format) throws IOException {
		int i = 0;
		while (i < xournal.getPages().size()) {
			renderer.export(filename + "_" + (i + 1), format, i++);
		}
	}

}
