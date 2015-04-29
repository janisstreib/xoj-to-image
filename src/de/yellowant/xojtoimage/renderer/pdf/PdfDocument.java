package de.yellowant.xojtoimage.renderer.pdf;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Anton Schirg
 */
public class PdfDocument {
	public List<PdfPage> pages = new LinkedList<>();
	public List<PdfIndirectObject> fonts = new LinkedList<>();
	final PdfIndirectObject rootObject;

	final PdfIndirectObject pagesObject;
	final PdfArray pagesArray;
	private int objectCounter = 0;
	private final PdfLong pageCount;

	public PdfDocument() {
		PdfDictionary catalogDict = new PdfDictionary();
		catalogDict.dict.put(new PdfName("Type"), new PdfName("Catalog"));
		rootObject = constructIndirectObject(catalogDict);

		PdfDictionary pagesDict = new PdfDictionary();
		pagesDict.dict.put(new PdfName("Type"), new PdfName("Pages"));
		pagesArray = new PdfArray();
		pagesDict.dict.put(new PdfName("Kids"), pagesArray);
		pagesObject = constructIndirectObject(pagesDict);
		pageCount = new PdfLong(pagesArray.elements.size());
		pagesDict.dict.put(new PdfName("Count"), pageCount);
		catalogDict.dict.put(new PdfName("Pages"), new PdfIndirectReference(
				pagesObject));
	}

	public void render(OutputStreamWriter out) throws IOException {
		out.write("%PDF-1.0\n\n");

		rootObject.render(out);
		out.write("\n\n");

		pagesObject.render(out);
		out.write("\n\n");

		for (PdfIndirectObject font : fonts) {
			font.render(out);
			out.write("\n");
		}
		out.write("\n");

		for (PdfPage page : pages) {
			page.render(out);
			out.write("\n");
		}
		out.write("\n");

		out.write("xref\n");
		out.write("0 ");
		out.write(objectCounter + 1);
		out.write("\n\n"); // Num object
							// incl xref

		out.write("trailer\n");
		PdfDictionary trailerDict = new PdfDictionary();
		trailerDict.dict.put(new PdfName("Size"),
				new PdfLong(objectCounter + 1));
		trailerDict.dict.put(new PdfName("Root"), new PdfIndirectReference(
				rootObject));
		trailerDict.render(out);
		out.write("\n");
		out.write("startxref\n\n");

		out.write("%%EOF");
	}

	public PdfIndirectObject constructIndirectObject(PdfObject object) {
		objectCounter++;
		return new PdfIndirectObject(object, objectCounter, 0);
	}

	public PdfPage addPage(double width, double height) {
		PdfPage page = new PdfPage(this, width, height);
		pages.add(page);
		pagesArray.elements.add(new PdfIndirectReference(page.page));
		pageCount.value = pagesArray.elements.size();
		return page;
	}

	public PdfIndirectObject addFont(String baseFont) {
		PdfDictionary fontDefDict = new PdfDictionary();
		fontDefDict.dict.put(new PdfName("Type"), new PdfName("Font"));
		fontDefDict.dict.put(new PdfName("Subtype"), new PdfName("Type1"));
		fontDefDict.dict.put(new PdfName("BaseFont"), new PdfName(baseFont));
		PdfIndirectObject indirectObject = constructIndirectObject(fontDefDict);
		fonts.add(indirectObject);
		return indirectObject;
	}
}
