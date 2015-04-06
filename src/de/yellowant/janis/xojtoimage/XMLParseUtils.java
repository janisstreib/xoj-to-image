package de.yellowant.janis.xojtoimage;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XMLParseUtils {
	public static String readGeneralSimpleTextTag(XmlPullParser parser,
			String namespace, String tag) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, namespace, tag);
		String summary = readText(parser);
		parser.require(XmlPullParser.END_TAG, namespace, tag);
		return summary;
	}

	public static String readText(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	public static void skip(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}
}
