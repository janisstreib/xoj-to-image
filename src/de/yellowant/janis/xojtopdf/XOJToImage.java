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

import de.yellowant.janis.xojtopdf.xournalelements.Page;
import de.yellowant.janis.xojtopdf.xournalelements.Xournal;

public class XOJToImage {
	private GZIPInputStream input;
	private Xournal xournal;
	private static final float FACTOR = 2;

	public static void main(String[] args) throws FileNotFoundException,
			IOException, XmlPullParserException {
		XOJToImage xoj = new XOJToImage(new File("test.xoj"));
		Xournal xour = xoj.getXournal();
		int pageNumber = 1;
		for (Page p : xour.getPages()) {
			PageCanvas comp = new PageCanvas(p, FACTOR);
			comp.exportImage("test_" + pageNumber++, "png");
		}
	}

	public XOJToImage(File input) throws FileNotFoundException, IOException,
			XmlPullParserException {
		this.input = new GZIPInputStream(new FileInputStream(input));
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
