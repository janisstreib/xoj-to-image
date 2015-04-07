package de.yellowant.xojtoimage.xournalelements;

import static de.yellowant.xojtoimage.XMLParseUtils.skip;

import java.io.IOException;
import java.util.LinkedList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class Xournal {
	private LinkedList<Page> pages = new LinkedList<Page>();

	public Xournal(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("page")) {
				pages.add(new Page(parser));
			} else {
				skip(parser);
			}
		}
	}

	public LinkedList<Page> getPages() {
		return pages;
	}

}
