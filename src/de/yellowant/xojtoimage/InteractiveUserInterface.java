package de.yellowant.xojtoimage;

import java.io.File;

import de.yellowant.xojtoimage.renderer.PageCanvas;

public class InteractiveUserInterface {
	private static final String USAGE = "Uasge:\njava -jar xojtoimage.jar <Xournal file> <format> <scale factor> <ouput file prefix>";

	public static void main(String[] args) {
		try {
			XOJToImage xoj = new XOJToImage(new File(args[0]), new PageCanvas(
					Float.parseFloat(args[2])));
			xoj.exportAll(args[3], args[1]);
			System.out.println("Done. Processed "
					+ xoj.getXournal().getPages().size() + " pages.");
		} catch (Exception e) {
			System.err.println("Error, please check your input! ("
					+ e.getMessage() + ")");
			System.out.println(USAGE);
		}
	}
}
