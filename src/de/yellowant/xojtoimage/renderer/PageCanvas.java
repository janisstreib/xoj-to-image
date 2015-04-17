package de.yellowant.xojtoimage.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import de.yellowant.xojtoimage.xournalelements.Layer;
import de.yellowant.xojtoimage.xournalelements.Page;
import de.yellowant.xojtoimage.xournalelements.Stroke;
import de.yellowant.xojtoimage.xournalelements.Text;
import de.yellowant.xojtoimage.xournalelements.Tool;

public class PageCanvas implements Renderer {
	private float factor;
	private LinkedList<Page> pages;

	public PageCanvas(float factor) {
		this.factor = factor;
	}

	private static class Line {
		final double x1;
		final double y1;
		final double x2;
		final double y2;
		final Color color;
		final double width;

		private Line(double x1, double y1, double x2, double y2, double width,
				Color color) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.color = color;
			this.width = width;
		}
	}

	private LinkedList<LinkedList<Line>> lines = new LinkedList<LinkedList<Line>>();
	private LinkedList<LinkedList<Text>> texts = new LinkedList<LinkedList<Text>>();

	public void paintXOJ(Graphics g, Page p, LinkedList<Text> ptexts,
			LinkedList<Line> plines) {
		for (Line line : plines) {
			g.setColor(line.color);
			Graphics2D g2d = (Graphics2D) g.create();
			Composite old = g2d.getComposite();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			Line2D.Double l = new Line2D.Double(line.x1, line.y1, line.x2,
					line.y2);
			g2d.setStroke(new BasicStroke((float) line.width,
					BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
			// g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.XOR,
			// line.color.getAlpha() / 255f));
			g2d.draw(l);
			g2d.setComposite(old);
			// rect = new Rectangle2D.Double(line.x1, line.y1,
			// Math.sqrt((line.y2 - line.y1) * (line.y2 - line.y1)
			// + (line.x2 - line.x1) * (line.x2 - line.x1)),
			// line.width);
			// g2d.fill(rect);
		}
		for (Text text : ptexts) {
			g.setColor(text.getColor().getAwtColor(Tool.PEN));
			g.setFont(text.getFont().deriveFont(
					text.getFont().getSize() * factor));
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			// Magic number here.
			float y = (float) (text.getY() - 4) * factor;
			for (String line : text.getText().split("\n")) {
				g2d.drawString(line, (float) text.getX() * factor, y += g
						.getFontMetrics().getHeight());
			}
		}
	}

	@Override
	public void export(String imageName, String format, int pageIndex)
			throws IOException {
		Page p = pages.get(pageIndex);
		int width = (int) Math.round(p.getWidth() * factor);
		int height = (int) (Math.round(p.getHeight() * factor));
		BufferedImage buf = new BufferedImage(height, width,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D d = buf.createGraphics();
		p.paintBackround(d, Tool.PEN, width, height, factor);
		paintXOJ(d, p, texts.get(pageIndex), lines.get(pageIndex));
		ImageIO.write(buf, format, new File(imageName + "." + format));
		d.dispose();
	}

	@Override
	public void setPages(LinkedList<Page> pages) {
		this.pages = pages;
		for (Page p : pages) {
			LinkedList<Text> pageTexts = new LinkedList<Text>();
			LinkedList<Line> pageLines = new LinkedList<PageCanvas.Line>();
			for (Layer l : p.getLayers()) {
				pageTexts.addAll(l.getTexts());
				for (Stroke stroke : l.getStrokes()) {
					Color awtColor = stroke.getColor().getAwtColor(
							stroke.getTool());
					double[] coords = stroke.getCoords();
					double[] widths = stroke.getWidths();
					for (int i = 0; i < coords.length - 2; i += 2) {
						double width;
						if (widths.length == 0) {
							width = 1;
						} else {
							if (i > widths.length - 1) {
								width = widths[widths.length - 1];
							} else {
								width = widths[i];
								if (i != 0) {
									width = widths[i - 1];
								}
							}
						}
						pageLines.add(new Line(coords[i] * factor,
								coords[i + 1] * factor, coords[i + 2] * factor,
								coords[i + 3] * factor, Math.max(width, 1)
										* factor, awtColor));
					}
				}
			}
			lines.add(pageLines);
			texts.add(pageTexts);
		}
	}

	@Override
	public void exportAll(String name, String format) throws IOException {
		for (int i = 0; i < pages.size(); i++) {
			export(name + "_" + i, format, i);
		}
	}
}
