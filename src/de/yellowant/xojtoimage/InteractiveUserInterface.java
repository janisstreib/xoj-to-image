package de.yellowant.xojtoimage;

import java.io.FileInputStream;
import java.util.regex.Pattern;

import de.yellowant.xojtoimage.renderer.PageCanvas;
import de.yellowant.xojtoimage.renderer.Renderer;
import de.yellowant.xojtoimage.renderer.pdf.PdfRenderer;

public class InteractiveUserInterface {
	private static final String USAGE = "Uasge:\njava -jar xojtoimage.jar <Xournal file> <destination file> [<scale factor>]";

	public static void main(String[] args) {
		try {
			Renderer r = null;
			String file = args[1].toLowerCase();
			if (file.endsWith(".pdf")) {
				r = new PdfRenderer();
			} else if (file.endsWith(".png") || file.endsWith(".gif")) {
				int factor = 1;
				if (args.length == 3) {
					factor = Integer.parseInt(args[2]);
				}
				r = new PageCanvas(factor);
			}
			if (r == null) {
				System.err.println("No known output format supplied!");
			}
			XOJToImage xoj = new XOJToImage(new FileInputStream(args[0]), r);
			String[] fileParts = args[1].split(Pattern.quote("."));
			xoj.exportAll(fileParts[fileParts.length - 2],
					fileParts[fileParts.length - 1]);
			System.out.println("Done. Processed "
					+ xoj.getXournal().getPages().size() + " pages.");
		} catch (Exception e) {
			System.err.println("Please check your input! (" + e.getMessage()
					+ ")");
			e.printStackTrace();
			System.out.println(USAGE);
		}
	}
}
