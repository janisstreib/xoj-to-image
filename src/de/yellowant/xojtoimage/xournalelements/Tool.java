package de.yellowant.xojtoimage.xournalelements;

public enum Tool {
	PEN(1), HIGHLIGHTER(0.5f), PAGELINER(.9f);

	private float opacity;

	Tool(float opacity) {
		this.opacity = opacity;
	}

	public int getAlpha() {
		return Math.round((255 / 100f) * (opacity * 100f));
	}

	public float getOpacity() {
		return opacity;
	}

	public static Tool getToolByName(String name) {
		switch (name.trim().toLowerCase()) {
		case "pen":
			return PEN;
		case "highlighter":
			return HIGHLIGHTER;
		default:
			return PEN;
		}
	}

}
