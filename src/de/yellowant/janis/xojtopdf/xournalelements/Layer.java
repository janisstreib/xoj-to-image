package de.yellowant.janis.xojtopdf.xournalelements;

import static de.yellowant.janis.xojtopdf.XMLParseUtils.skip;

import java.io.IOException;
import java.util.LinkedList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class Layer {
	private LinkedList<Stroke> strokes = new LinkedList<Stroke>();

	public Layer(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		parser.require(XmlPullParser.START_TAG, null, "layer");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("stroke")) {
				strokes.add(new Stroke(parser));
			} else {
				skip(parser);
			}
		}
	}

	public LinkedList<Stroke> getStrokes() {
		return strokes;
	}

}
