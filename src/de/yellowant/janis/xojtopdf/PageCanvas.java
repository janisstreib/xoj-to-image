package de.yellowant.janis.xojtopdf;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class PageCanvas {
	private int width, height;

	public PageCanvas(int width, int height) {
		this.height = height;
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	private static class Line {
		final double x1;
		final double y1;
		final double x2;
		final double y2;
		final Color color;
		final double width;

		public Line(double x1, double y1, double x2, double y2, double width,
				Color color) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.color = color;
			this.width = width;
		}
	}

	private final LinkedList<Line> lines = new LinkedList<Line>();

	public void addLine(double x1, double x2, double x3, double x4, double width) {
		addLine(x1, x2, x3, x4, width, Color.black);
	}

	public void addLine(double x1, double x2, double x3, double x4,
			double width, Color color) {
		lines.add(new Line(x1, x2, x3, x4, width, color));
	}

	public void clearLines() {
		lines.clear();
	}

	public void paintXOJ(Graphics g) {
		for (Line line : lines) {
			g.setColor(line.color);
			Line2D.Double l = new Line2D.Double(line.x1, line.y1, line.x2,
					line.y2);
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.setStroke(new BasicStroke((float) line.width));
			g2d.draw(l);
			// rect = new Rectangle2D.Double(line.x1, line.y1,
			// Math.sqrt((line.y2 - line.y1) * (line.y2 - line.y1)
			// + (line.x2 - line.x1) * (line.x2 - line.x1)),
			// line.width);
			// g2d.fill(rect);
		}
	}

	public void exportImage(String imageName) throws IOException {
		BufferedImage buf = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D d = buf.createGraphics();
		paintXOJ(d);
		ImageIO.write(buf, "png", new File(imageName));
		d.dispose();
	}
}
