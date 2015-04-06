package de.yellowant.janis.xojtoimage.xournalelements;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import de.yellowant.janis.xojtoimage.XMLParseUtils;

public class Stroke {
	private double[] coords;
	private double[] widths;
	private Tool tool;
	private Color color;

	public Stroke(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		tool = Tool.getToolByName(parser.getAttributeValue(null, "tool"));
		color = Color.getColorByName(parser.getAttributeValue(null, "color"));
		widths = parseBunch(parser.getAttributeValue(null, "width"));
		coords = parseBunch(XMLParseUtils.readText(parser));
	}

	private double[] parseBunch(String input) throws IOException,
			XmlPullParserException {
		String[] parse = input.trim().split(" ");
		double[] res = new double[parse.length];
		for (int i = 0; i < parse.length; i++) {
			res[i] = Double.parseDouble(parse[i]);
		}
		return res;
	}

	public double[] getCoords() {
		return coords;
	}

	public double[] getWidths() {
		return widths;
	}

	public Tool getTool() {
		return tool;
	}

	public Color getColor() {
		return color;
	}
}
