package de.yellowant.janis.xojtopdf.xournalelements;

public enum Color {
	BLACK(java.awt.Color.BLACK), YELLOW(java.awt.Color.YELLOW), ORANGE(
			java.awt.Color.ORANGE);
	
	private java.awt.Color awtColor;

	Color(java.awt.Color awtColor) {
		this.awtColor = awtColor;
	}

	public java.awt.Color getAwtColor() {
		return awtColor;
	}

	public static Color getColorByName(String name) {
		switch (name.trim().toLowerCase()) {
		case "black":
			return BLACK;
		case "yellow":
			return YELLOW;
		case "orange":
			return ORANGE;
		default:
			return BLACK;
		}
	}
}
