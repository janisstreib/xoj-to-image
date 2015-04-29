package de.yellowant.xojtoimage.renderer.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

import de.yellowant.xojtoimage.renderer.Renderer;
import de.yellowant.xojtoimage.xournalelements.Layer;
import de.yellowant.xojtoimage.xournalelements.Page;
import de.yellowant.xojtoimage.xournalelements.Stroke;

/**
 * @author Anton Schirg
 */
public class PdfRenderer implements Renderer {
	PdfDocument doc;
	private LinkedList<Page> xojPages;

	@Override
	public void export(String name, String format, int pageIndex)
			throws IOException {
		throw new IllegalArgumentException(
				"Singe page pdf export not suppoerted yet!");
	}

	private double[] reverseCoordinates(double[] coordinates, double newOrigin) {
		double[] result = new double[coordinates.length];
		for (int i = 0; i < coordinates.length; i++) {
			result[i] = newOrigin - coordinates[i];
		}
		return result;
	}

	private double[] getEven(double[] array) {
		int num = (int) Math.ceil(array.length / 2.0);
		double[] result = new double[num];
		for (int i = 0; i < num; i++) {
			result[i] = array[i * 2];
		}
		return result;
	}

	private double[] getOdd(double[] array) {
		int num = (int) Math.floor(array.length / 2.0);
		double[] result = new double[num];
		for (int i = 0; i < num; i++) {
			result[i] = array[i * 2 + 1];
		}
		return result;
	}

	@Override
	public void setPages(LinkedList<Page> pages) {
		this.xojPages = pages;
	}

	@Override
	public void exportAll(String name, String format) throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
				new File(name + ".pdf")));
		render(out);
		out.flush();
		out.close();
	}

	private void render(OutputStreamWriter out) throws IOException {
		doc = new PdfDocument();
		for (Page xojPage : xojPages) {
			double pageWidth = xojPage.getHeight(); // Switched (switch back
													// some time?)
			double pageHeight = xojPage.getWidth();
			PdfPage pdfPage = doc.addPage(pageWidth, pageHeight);
			for (Layer layer : xojPage.getLayers()) {
				for (Stroke stroke : layer.getStrokes()) {
					pdfPage.setStrokeColor(stroke.getColor().getAwtColor());
					pdfPage.setAlpha(stroke.getTool().getOpacity());
					double[] xCoords = getEven(stroke.getCoords());
					double[] yCoords = reverseCoordinates(
							getOdd(stroke.getCoords()), pageHeight);
					if (stroke.getWidths().length == 1) {
						pdfPage.setStrokeWidth(stroke.getWidths()[0]);
						pdfPage.drawStroke(xCoords, yCoords);
					} else {
						pdfPage.drawStrokeVaryingWidth(xCoords, yCoords,
								stroke.getWidths());
					}
				}
			}
		}

		doc.render(out);
	}

	@Override
	public void exportAsStream(String format, int pageIndex, OutputStream out)
			throws IOException {
		OutputStreamWriter out2 = new OutputStreamWriter(out);
		render(out2);
		out2.flush();
	}
}
