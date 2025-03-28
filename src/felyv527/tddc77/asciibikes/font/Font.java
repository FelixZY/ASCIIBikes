package felyv527.tddc77.asciibikes.font;

/**
 * An ascii font.
 * 
 * <p>
 * Allows the generation of ascii art from strings.
 */
public abstract class Font {

	/**
	 * Returns the corresponding ascii art glyph for a character.
	 * 
	 * <p>
	 * All glyphs in a font must be of uniform size.
	 * 
	 * <p>
	 * Newlines are handled in {@link Font}
	 * 
	 * @param chr
	 *            The char for which to retrieve a glyph
	 * @return A glyph corresponding to the char parameter
	 */
	protected abstract String[] getGlyph(char chr);

	/**
	 * Applies this font to a string.
	 * 
	 * @param str
	 *            The string to represent with this font.
	 * @return An ascii art representation of the parameter string.
	 */
	public String[] applyFont(String str) {
		/*
		 * In order to respect newlines, we split the string into its line
		 * components.
		 */
		String[] splitStr = str.split("\n");
		String[][] lines = new String[splitStr.length][];

		// We then apply this font to each of the lines
		for (int i = 0; i < splitStr.length; i++)
			lines[i] = buildLine(splitStr[i]);

		/*
		 * Finally we join all lines together.
		 */
		return joinLines(lines);
	}

	/**
	 * Retrieves all required glyphs for a certain line of text and joins them
	 * together.
	 * 
	 * @param line
	 *            The line of text to represent with this font
	 * @return A single line of text represented with this font
	 */
	private String[] buildLine(String line) {
		return joinGlyphs(getGlyphArray(line.toCharArray()));
	}

	/**
	 * Retrieves all glyphs required for a certain line of text.
	 * 
	 * @param letterArray
	 *            The characters to represent with glyphs
	 * @return An array of the required glyphs.
	 * 
	 *         <p>
	 *         The first index of the resulting 2d array represents the
	 *         character index of the original string. The string array found at
	 *         this index represents the corresponding character glyph.
	 * 
	 */
	private String[][] getGlyphArray(char[] letterArray) {
		String[][] glyphs = new String[letterArray.length][];
		for (int i = 0; i < letterArray.length; i++) {
			glyphs[i] = getGlyph(letterArray[i]);
		}
		return glyphs;
	}

	/**
	 * Joins an array of glyphs into a single line of text.
	 * 
	 * @param glyphs
	 *            The glyphs to join together
	 * @return A string array containing all glyphs joined together into a
	 *         single line of text
	 * 
	 *         <p>
	 *         Note that the length of the resulting array will be the same as
	 *         the length an individual glyph, meaning that if the glyphs are 5
	 *         rows high (have a length of 5) the resulting array will also have
	 *         a height (length) of 5.
	 */
	private static String[] joinGlyphs(String[][] glyphs) {
		if (glyphs.length == 0)
			return new String[] { "" };

		String[] out = new String[glyphs[0].length];

		/*
		 * Each glyph consists of an array of strings.
		 * 
		 * What is basically done is that the first row of each glyph is joined
		 * together into a single string, then the next and so non.
		 */
		for (int glyphRow = 0; glyphRow < glyphs[0].length; glyphRow++) {
			StringBuilder outRow = new StringBuilder();

			for (int glyphIndex = 0; glyphIndex < glyphs.length; glyphIndex++)
				outRow.append(glyphs[glyphIndex][glyphRow]);

			out[glyphRow] = outRow.toString();
		}

		return out;
	}

	/**
	 * Joins together multiple lines of glyphs into a single array.
	 * 
	 * @param lines
	 *            The lines of glyphs to join together
	 * @return A one dimensional array containing all input lines joined
	 *         together.
	 */
	private static String[] joinLines(String[][] lines) {
		String[] out = new String[lines.length * lines[0].length];

		for (int i = 0, outIndex = 0; i < lines.length; i++)
			for (int j = 0; j < lines[i].length; j++)
				out[outIndex++] = lines[i][j];

		return out;
	}
}
