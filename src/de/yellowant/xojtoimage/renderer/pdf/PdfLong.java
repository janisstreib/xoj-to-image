package de.yellowant.xojtoimage.renderer.pdf;

/**
 * @author Anton Schirg
 */
public class PdfLong extends PdfObject {
	public long value;

	public PdfLong(long value) {
		this.value = value;
	}

	@Override
	public String render() {
		return value + "";
	}
}
