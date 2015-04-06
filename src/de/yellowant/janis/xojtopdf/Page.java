package de.yellowant.janis.xojtopdf;

import java.io.IOException;
import java.util.LinkedList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class Page {
	private double width, height;
	private LinkedList<Layer> layers = new LinkedList<Layer>();

	public Page(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		parser.require(XmlPullParser.START_TAG, null, "page");
		width = Double.parseDouble(parser.getAttributeValue(null, "width"));
		height = Double.parseDouble(parser.getAttributeValue(null, "height"));
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals("layer")) {
				layers.add(new Layer(parser));
			} else {
				XMLParseUtils.skip(parser);
			}
		}
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public LinkedList<Layer> getLayers() {
		return layers;
	}

}
