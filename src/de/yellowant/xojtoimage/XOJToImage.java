package de.yellowant.xojtoimage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import de.yellowant.xojtoimage.renderer.Renderer;
import de.yellowant.xojtoimage.xournalelements.Xournal;

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
		renderer.exportAll(filename, format);
	}

	public void exportAsStream(String format, int page, OutputStream out)
			throws IOException {
		renderer.exportAsStream(format, page, out);
	}

	public int getPageCount() {
		return xournal.getPages().size();
	}

}
