package felyv527.tddc77.asciibikes;

/**
 * {@link StringTools} provides methods for {@link String} generation and
 * manipulation.
 *
 */
public class StringTools {

	private StringTools() {
		// Utility class. Should not be instantiated.
	}

	/**
	 * Repeats a given <code>char</code> <code>n</code> times.
	 * 
	 * @param chr
	 *            The char to repeat
	 * @param n
	 *            The number of times to repeat the char
	 * @return A new string consisting of the repeated char sequence
	 */
	public static String repeat(char chr, int n) {
		if (n < 1)
			return "";
		else
			return new String(new char[n]).replace('\0', chr);
	}

	/**
	 * Wraps text to a certain line width, respecting paragraphs.
	 * 
	 * @param str
	 *            The string to wrap
	 * @param lineWidth
	 *            The width of the lines after which to wrap the string.
	 * @return The input string wrapped according to the specified line width.
	 */
	public static String wrap(String str, int lineWidth) {
		String[] lines = str.split("\n");
		StringBuilder output = new StringBuilder();

		for (String line : lines) {
			int start = 0;

			while (start < line.length()) {

				int subEnd = Math.min(line.length(), start + lineWidth);
				String subLine = line.substring(start, subEnd);

				if (subLine.length() == line.length() - start) {
					// This subLine is the last in the current line
					output.append(subLine);
					start = line.length();
				} else {
					// This subLine is not the last in the current line
					subEnd = subLine.lastIndexOf(' ');

					if (subEnd < 0) {
						/*
						 * Whitespace was not found in the current substring,
						 * meaning the line will be wrapped in the middle of a
						 * word.
						 */
						subEnd = lineWidth;
						start = start + subEnd;
					} else {
						/*
						 * Whitespace was found in the current substring. Next
						 * time we will want to start our substring from after
						 * this whitespace, hence the + 1
						 */
						start = start + subEnd + 1;
					}

					output.append(subLine.substring(0, subEnd) + "\n");
				}
			}
			output.append('\n');
		}

		// Removes the last trailing newline
		output.setLength(output.length() - 1);

		return output.toString();
	}

	/**
	 * Generates a frame based on a rectangle.
	 * 
	 * <p>
	 * The resulting String[] will have the same length as the input
	 * {@link Rectangle} height. The Strings inside the String[] will be of the
	 * same length as the {@link Rectangle} width
	 * 
	 * @param area
	 *            The area to frame
	 * @return A {@code String[]} containing a frame.
	 */
	public static String[] frame(Rectangle area) {
		StringBuilder[] frameBuilder = new StringBuilder[area.getHeight()];

		for (int i = 0; i < frameBuilder.length; i++) {
			frameBuilder[i] = new StringBuilder();
		}

		if (area.getHeight() == 1) {

			if (area.getWidth() == 1) {
				// 1x1 square

				frameBuilder[0].append("▯");

			} else if (area.getWidth() > 1) {
				// Horizontal line
				frameBuilder[0].append("╞");
				frameBuilder[0].append(StringTools.repeat('═', area.getWidth() - 2));
				frameBuilder[0].append("╡");
			}

		} else if (area.getHeight() > 1) {

			if (area.getWidth() == 1) {
				// Vertical line
				frameBuilder[0].append("╥");

				for (int i = 1; i < frameBuilder.length - 1; i++)
					frameBuilder[i].append("║");

				frameBuilder[area.getHeight() - 1].append("╨");

			} else if (area.getWidth() > 1) {
				// Rectangle larger than 1x1

				frameBuilder[0].append("╭");
				frameBuilder[0].append(StringTools.repeat('─', area.getWidth() - 2));
				frameBuilder[0].append("╮");

				for (int i = 1; i < frameBuilder.length - 1; i++) {
					frameBuilder[i].append("│");
					frameBuilder[i].append(StringTools.repeat(' ', area.getWidth() - 2));
					frameBuilder[i].append("│");
				}

				frameBuilder[frameBuilder.length - 1].append("╰");
				frameBuilder[frameBuilder.length - 1].append(StringTools.repeat('─', area.getWidth() - 2));
				frameBuilder[frameBuilder.length - 1].append("╯");

			}

		}

		String[] frame = new String[area.getHeight()];

		for (int i = 0; i < frameBuilder.length; i++)
			frame[i] = frameBuilder[i].toString();

		return frame;
	}

	/**
	 * Centers a string based on the specified lineWidth.
	 * 
	 * The resulting string will be padded with whitespace on the left and
	 * right.
	 * 
	 * @param str
	 *            The string to center
	 * @param lineWidth
	 *            The width of the line the string is to be centered on
	 * @return The input string padded with whitespace so that it is centered
	 */
	public static String center(String str, int lineWidth) {
		return center(str, lineWidth, ' ');
	}

	/**
	 * Centers a string based on the specified lineWidth.
	 * 
	 * The resulting string will be padded with the specified
	 * {@code paddingCharacter} on the left and right.
	 * 
	 * @param str
	 *            The string to center
	 * @param lineWidth
	 *            The width of the line the string is to be centered on
	 * @param paddingCharacter
	 *            The character to pad the area around the string
	 * @return The input string padded with {@code paddingCharacter} so that it
	 *         is centered
	 */
	public static String center(String str, int lineWidth, char paddingCharacter) {

		int diff = lineWidth - str.length();
		int leftPadding = (int) Math.floor(diff / 2.0);
		int rightPadding = (int) Math.ceil(diff / 2.0);

		return StringTools.repeat(paddingCharacter, leftPadding) + str
				+ StringTools.repeat(paddingCharacter, rightPadding);
	}

	/**
	 * Centers all strings in an array based on the specified lineWidth.
	 * 
	 * The resulting strings will be padded with whitespace on the left and
	 * right.
	 * 
	 * @param str_arr
	 *            The string array to center
	 * @param lineWidth
	 *            The width of the line the strings are to be centered on
	 * @return An array of strings padded with whitespace to accomplish uniform
	 *         length
	 */
	public static String[] center(String[] str_arr, int lineWidth) {
		return center(str_arr, lineWidth, ' ');
	}

	/**
	 * Centers all strings in an array based on the specified lineWidth.
	 * 
	 * The resulting string will be padded with the specified
	 * {@code paddingCharacter} on the left and right.
	 * 
	 * @param str_arr
	 *            The string array to center
	 * @param lineWidth
	 *            The width of the line the strings are to be centered on
	 * @param paddingCharacter
	 *            The character to pad the area around the string
	 * @return An array of strings padded with {@code paddingCharacter} to
	 *         accomplish uniform length
	 */
	public static String[] center(String[] str_arr, int lineWidth, char paddingCharacter) {
		for (int i = 0; i < str_arr.length; i++) {
			str_arr[i] = StringTools.center(str_arr[i], lineWidth, paddingCharacter);
		}
		return str_arr;
	}
}
