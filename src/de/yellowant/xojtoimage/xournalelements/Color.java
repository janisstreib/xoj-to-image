package de.yellowant.xojtoimage.xournalelements;

public enum Color {
	BLACK(java.awt.Color.BLACK), YELLOW(java.awt.Color.YELLOW), ORANGE(
			java.awt.Color.ORANGE), RED(java.awt.Color.RED), BLUE(
			java.awt.Color.BLUE), WHITE(java.awt.Color.WHITE), GRAY(
			java.awt.Color.GRAY), LIGHTBLUE(java.awt.Color.CYAN), LIGHTGREEN(
			java.awt.Color.decode("0x3dff00")), MAGENTA(java.awt.Color.MAGENTA), GREEN(
			java.awt.Color.decode("0x1a7100"));
	
	private java.awt.Color awtColor;

	Color(java.awt.Color awtColor) {
		this.awtColor = awtColor;
	}

	public java.awt.Color getAwtColor(Tool tool) {
		return new java.awt.Color(awtColor.getRed(), awtColor.getGreen(),
				awtColor.getBlue(), tool.getAlpha());
	}

	public static Color getColorByName(String name) {
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
