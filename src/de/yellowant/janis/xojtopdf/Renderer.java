package de.yellowant.janis.xojtopdf;

import java.io.IOException;
import java.util.LinkedList;

import de.yellowant.janis.xojtopdf.xournalelements.Page;

public interface Renderer {
	public void export(String name, String format, int pageIndex)
			throws IOException;

	public void setPages(LinkedList<Page> pages);
}
