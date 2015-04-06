package de.yellowant.janis.xojtoimage.xournalelements;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.LinkedList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import de.yellowant.janis.xojtoimage.XMLParseUtils;

public class Page {
	private double width, height;
	private LinkedList<Layer> layers = new LinkedList<Layer>();
	private Color backroundColor;
	private String style;

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
				style = parser.getAttributeValue(null, "style");
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

	public void paintBackround(Graphics2D g, Tool t, int width, int height,
			float factor) {
		g.setBackground(backroundColor.getAwtColor(t));
		g.clearRect(0, 0, height, width);
		if ("lined".equals(style)) {
			java.awt.Stroke oldStroke = g.getStroke();
			g.setStroke(new BasicStroke(1.1f));
			g.setColor(Color.MAGENTA.getAwtColor(Tool.PAGELINER));
			int round = Math.round(72 * factor);
			g.drawLine(round, 0, round, width);
			ruled(g, height, factor);
			g.setStroke(oldStroke);
		} else if ("graph".equals(style)) {
			java.awt.Stroke oldStroke = g.getStroke();
			g.setStroke(new BasicStroke(1.1f));
			g.setColor(Color.BLUE.getAwtColor(Tool.PAGELINER));
			int y = Math.round(14 * factor);
			for (int i = 0; i < height / 20; i++) {
				g.drawLine(0, y, height, y);
				g.drawLine(y, 0, y, width);
				y += Math.round(14 * factor);
			}
			g.setStroke(oldStroke);
		} else if ("ruled".equals(style)) {
			ruled(g, height, factor);
		}

	}

	private void ruled(Graphics2D g, int height, float factor) {
		java.awt.Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(1.1f));
		g.setColor(Color.BLUE.getAwtColor(Tool.PAGELINER));
		int y = Math.round(80 * factor);
		for (int i = 0; i < height / 10; i++) {
			g.drawLine(0, y, height, y);
			y += Math.round(24f * factor);
		}
		g.setStroke(oldStroke);
	}

}
