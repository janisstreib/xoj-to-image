package de.yellowant.xojtoimage.xournalelements;

public class Color {
	public static final Color BLACK = new Color(java.awt.Color.BLACK);
	public static final Color YELLOW = new Color(java.awt.Color.YELLOW);
	public static final Color ORANGE = new Color(java.awt.Color.ORANGE);
	public static final Color RED = new Color(java.awt.Color.RED);
	public static final Color BLUE = new Color(java.awt.Color.BLUE);
	public static final Color WHITE = new Color(java.awt.Color.WHITE);
	public static final Color GRAY = new Color(java.awt.Color.GRAY);
	public static final Color LIGHTBLUE = new Color(java.awt.Color.CYAN);
	public static final Color LIGHTGREEN = new Color(
			java.awt.Color.decode("0x3dff00"));
	public static final Color MAGENTA = new Color(java.awt.Color.MAGENTA);
	public static final Color GREEN = new Color(
			java.awt.Color.decode("0x1a7100"));

	private java.awt.Color awtColor;

	Color(java.awt.Color awtColor) {
		this.awtColor = awtColor;
	}

	public java.awt.Color getAwtColor(Tool tool) {
		return new java.awt.Color(awtColor.getRed(), awtColor.getGreen(),
				awtColor.getBlue(), tool.getAlpha());
	}

	public java.awt.Color getAwtColor() {
		return awtColor;
	}

	public static Color getColorByName(String name) {
		if (name.startsWith("#")) {
			name = name.replace("#", "0x");
			return new Color(java.awt.Color.decode(name.substring(0, 8)));
		}
		switch (name.trim().toLowerCase()) {
		case "black":
			return BLACK;
		case "yellow":
			return YELLOW;
		case "orange":
			return ORANGE;
		case "red":
			return RED;
		case "gray":
			return GRAY;
		case "lightblue":
			return LIGHTBLUE;
		case "lightgreen":
			return LIGHTGREEN;
		case "magenta":
			return MAGENTA;
		case "white":
			return WHITE;
		case "green":
			return GREEN;
		case "blue":
			return BLUE;
		default:
			return BLACK;
		}
	}
}
