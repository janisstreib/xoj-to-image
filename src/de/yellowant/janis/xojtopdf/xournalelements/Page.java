package de.yellowant.janis.xojtopdf.xournalelements;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.LinkedList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import de.yellowant.janis.xojtopdf.XMLParseUtils;

public class Page {
	private double width, height;
	private LinkedList<Layer> layers = new LinkedList<Layer>();
	private Color backroundColor;

	public Page(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		parser.require(XmlPullParser.START_TAG, null, "page");
		height = Double.parseDouble(parser.getAttributeValue(null, "width"));
		width = Double.parseDouble(parser.getAttributeValue(null, "height"));
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals("layer")) {
				layers.add(new Layer(parser));
			} else if (name.equals("background")) {
				backroundColor = Color.getColorByName(parser.getAttributeValue(
						null, "color"));
				XMLParseUtils.skip(parser);
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

	public void paintBackround(Graphics2D g, Tool t, int width, int height) {
		g.setBackground(backroundColor.getAwtColor(t));
		g.clearRect(0, 0, height, width);
	}

}
