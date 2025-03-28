package felyv527.tddc77.asciibikes.text;

import felyv527.tddc77.asciibikes.common.StringTools;

/**
 * Eftersom denna klass skall vara en abstrakt basklass kan tyvärr inte alla
 * metoder hållas statiska. Så mycket som möjligt är satt som statiskt men allt
 * går tyvärr inte.
 */
public abstract class Font {

	protected abstract String[] getGlyph(char chr);

	public String[] applyFont(String str) {
		return applyFont(str, TextAlignment.LEFT);
	}

	public String[] applyFont(String str, TextAlignment alignment) {
		String[] splitStr = str.split("\n");
		String[][] lines = new String[splitStr.length][];

		for (int i = 0; i < splitStr.length; i++)
			lines[i] = buildLine(splitStr[i]);

		return joinLines(lines, alignment);
	}

	private String[] buildLine(String word) {
		return joinGlyphs(getGlyphArray(word.toCharArray()));
	}

	private String[][] getGlyphArray(char[] letterArray) {
		String[][] glyphs = new String[letterArray.length][];
		for (int i = 0; i < letterArray.length; i++) {
			glyphs[i] = getGlyph(letterArray[i]);
		}
		return glyphs;
	}

	private static String[] joinGlyphs(String[][] glyphs) {
		if (glyphs.length == 0)
			return new String[] { "" };

		String[] out = new String[glyphs[0].length];

		for (int glyphRow = 0; glyphRow < glyphs[0].length; glyphRow++) {
			StringBuilder outRow = new StringBuilder();
			for (int glyphIndex = 0; glyphIndex < glyphs.length; glyphIndex++)
				outRow.append(glyphs[glyphIndex][glyphRow]);
			out[glyphRow] = outRow.toString();
		}

		return out;
	}

	private static String[] joinLines(String[][] lines, TextAlignment alignment) {
		lines = alignLines(lines, alignment);

		// Vi förutsätter här att alla tecken har samma höjd
		String[] out = new String[lines.length * lines[0].length];

		for (int i = 0, outIndex = 0; i < lines.length; i++)
			for (int j = 0; j < lines[i].length; j++)
				out[outIndex++] = lines[i][j];

		return out;
	}

	private static String[][] alignLines(String[][] lines, TextAlignment alignment) {
		if (lines.length > 1) {
			int longestLine = getLongestLine(lines);

			for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
				int diff = longestLine - getLineLength(lines[lineIndex]);
				if (diff > 0) {
					int rightPadding = 0;
					int leftPadding = 0;

					switch (alignment) {

					default:
					case CENTER:
						leftPadding = (int) Math.ceil(diff / 2.0);
						rightPadding = (int) Math.floor(diff / 2.0);
						break;

					case LEFT:
						rightPadding = diff;
						break;

					case RIGHT:
						leftPadding = diff;
						break;

					}

					for (int i = 0; i < lines[lineIndex].length; i++) {
						lines[lineIndex][i] = (StringTools.repeat(" ", leftPadding)) + lines[lineIndex][i]
								+ (StringTools.repeat(" ", rightPadding));
					}

				}
			}
		}
		return lines;
	}

	private static int getLongestLine(String[][] lines) {
		int longestLine = 0;
		for (String[] line : lines)
			longestLine = Math.max(getLineLength(line), longestLine);
		return longestLine;
	}

	private static int getLineLength(String[] line) {
		if (line.length > 0)
			return line[0].length();
		else
			return 0;
	}
}
