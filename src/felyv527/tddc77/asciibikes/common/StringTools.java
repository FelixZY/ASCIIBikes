package felyv527.tddc77.asciibikes.common;

public class StringTools {

	/**
	 * Repeats a given <code>CharSequence</code> <code>n</code> times.
	 * 
	 * @param sequence
	 *            The sequence to repeat
	 * @param n
	 *            The number of times to repeat the sequence
	 * @return A new string consisting of the repeated char sequence
	 */
	public static String repeat(CharSequence sequence, int n) {
		if (n < 1)
			return "";
		else
			return new String(new char[n]).replace("\0", sequence);
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
	 * Wraps a string to a certain length, respecting paragraphs
	 * 
	 * @param str
	 * @param lineWidth
	 * @return
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
					// We've reached the final piece of the string
					output.append(subLine);
					start += lineWidth;
				} else {
					// We're working in the middle of the string.
					subEnd = subLine.lastIndexOf(' ');

					if (subEnd < 0) {
						subEnd = lineWidth;
						start = start + subEnd;
					} else
						start = start + subEnd + 1;

					subLine = subLine.substring(0, subEnd) + "\n";

					output.append(subLine);
				}
			}
			output.append('\n');
		}

		output.setLength(output.length() - 1);

		return output.toString();
	}

	/**
	 * Frames a specified area. The frame can be padded.
	 * 
	 * The resulting String[] will be of the same "size" as the input Rectangle.
	 * 
	 * @param area
	 *            The area to frame
	 * @param padding
	 *            the padding to keep around the frame and the edge
	 * 
	 *            Returns a <code>String[]</code> containing a frame.
	 */
	public static String[] frame(Rectangle area, int padding) {
		padding = Math.max(padding, 0);

		Rectangle paddedArea = new Rectangle(0, 0, area.getWidth() - padding * 2, area.getHeight() - padding * 2);

		StringBuilder[] frameBuilder = new StringBuilder[paddedArea.getHeight()];
		for (int i = 0; i < frameBuilder.length; i++) {
			frameBuilder[i] = new StringBuilder();

			if (i > 1 && i < frameBuilder.length - 1)
				frameBuilder[i].append("");
		}

		if (paddedArea.getHeight() == 1) {

			if (paddedArea.getWidth() == 1)
				frameBuilder[0].append("▯");

			else if (paddedArea.getWidth() > 1) {
				frameBuilder[0].append("╞");
				frameBuilder[0].append(StringTools.repeat("═", paddedArea.getWidth() - 2));
				frameBuilder[0].append("╡");
			}

		} else if (paddedArea.getHeight() > 1) {

			if (paddedArea.getWidth() == 1) {
				frameBuilder[0].append("╥");

				for (int i = 1; i < frameBuilder.length - 1; i++)
					frameBuilder[i].append("║");

				frameBuilder[paddedArea.getHeight() - 1].append("╨");

			} else if (paddedArea.getWidth() > 1) {

				frameBuilder[0].append("╭");
				frameBuilder[0].append(StringTools.repeat("─", paddedArea.getWidth() - 2));
				frameBuilder[0].append("╮");

				for (int i = 1; i < frameBuilder.length - 1; i++) {
					frameBuilder[i].append("│");
					frameBuilder[i].append(StringTools.repeat(" ", paddedArea.getWidth() - 2));
					frameBuilder[i].append("│");
				}

				frameBuilder[frameBuilder.length - 1].append("╰");
				frameBuilder[frameBuilder.length - 1].append(StringTools.repeat("─", paddedArea.getWidth() - 2));
				frameBuilder[frameBuilder.length - 1].append("╯");

			}

		}

		String[] frame = new String[area.getHeight()];

		for (int i = 0; i < area.getHeight(); i++) {
			if (i < padding || i >= padding + frameBuilder.length)
				frame[i] = StringTools.repeat(" ", area.getWidth());
			else
				frame[i] = StringTools.center(frameBuilder[i - padding].toString(), area.getWidth());
		}

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
	 */
	public static String center(String str, int lineWidth) {
		return center(str, ' ', lineWidth);
	}

	/**
	 * Centers a string based on the specified lineWidth.
	 * 
	 * The resulting string will be padded with the specified
	 * <code>paddingCharacter</code> on the left and right.
	 * 
	 * @param str
	 *            The string to center
	 * @param paddingCharacter
	 *            The character to pad the area around the string
	 * @param lineWidth
	 *            The width of the line the string is to be centered on
	 */
	public static String center(String str, char paddingCharacter, int lineWidth) {

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
	 * @return An array of uniform length strings
	 */
	public static String[] center(String[] str_arr, int lineWidth) {
		return center(str_arr, ' ', lineWidth);
	}

	/**
	 * Centers all strings in an array based on the specified lineWidth.
	 * 
	 * The resulting string will be padded with the specified
	 * <code>paddingCharacter</code> on the left and right.
	 * 
	 * @param str_arr
	 *            The string array to center
	 * @param paddingCharacter
	 *            The character to pad the area around the string
	 * @param lineWidth
	 *            The width of the line the strings are to be centered on
	 */
	public static String[] center(String[] str_arr, char paddingCharacter, int lineWidth) {
		for (int i = 0; i < str_arr.length; i++) {
			str_arr[i] = StringTools.center(str_arr[i], paddingCharacter, lineWidth);
		}
		return str_arr;
	}
}
