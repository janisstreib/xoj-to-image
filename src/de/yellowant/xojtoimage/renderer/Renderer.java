package de.yellowant.xojtoimage.renderer;

import java.io.IOException;
import java.util.LinkedList;

import de.yellowant.xojtoimage.xournalelements.Page;

public interface Renderer {
	public void export(String name, String format, int pageIndex)
			throws IOException;

	public void exportAll(String name, String format) throws IOException;

	public void setPages(LinkedList<Page> pages);
}
