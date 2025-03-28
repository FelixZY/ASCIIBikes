package felyv527.tddc77.asciibikes.text;

public final class StandardFont extends Font {

	@Override
	protected String[] getGlyph(char chr) {
		return new String[] { Character.toString(chr) };
	}
}
