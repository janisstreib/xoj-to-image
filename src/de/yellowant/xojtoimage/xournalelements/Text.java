package de.yellowant.xojtoimage.xournalelements;

import java.awt.Font;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import de.yellowant.xojtoimage.XMLParseUtils;

public class Text {
	private Font font;
	private double x, y;
	private String text;
	private Color color;

	public Text(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		font = new Font(parser.getAttributeValue(null, "font"), Font.PLAIN,
				Math.round(Float.parseFloat(parser.getAttributeValue(null,
						"size"))));
		x = Double.parseDouble(parser.getAttributeValue(null, "x"));
		y = Double.parseDouble(parser.getAttributeValue(null, "y"));
		color = Color.getColorByName(parser.getAttributeValue(null, "color"));
		text = XMLParseUtils.readText(parser);
	}

	public Font getFont() {
		return font;
	}

	public String getText() {
		return text;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Color getColor() {
		return color;
	}
}
