package felyv527.tddc77.asciibikes.colors;

/**
 * Represents all possible background colors that can be used to color text in
 * the terminal.
 *
 */
public enum TerminalBackgroundColor {
	/*
	 * There are more colors in most terminals, but these are the ones I got to
	 * work during development.
	 */

	BLACK("\u001B[40m"), RED("\u001B[41m"), GREEN("\u001B[42m"), YELLOW("\u001B[43m"), BLUE("\u001B[44m"),
	MAGENTA("\u001B[45m"), CYAN("\u001B[46m"), WHITE("\u001B[47m"), DEFAULT("\u001B[48m");

	final String escapeSequence;

	TerminalBackgroundColor(String escapeSequence) {
		this.escapeSequence = escapeSequence;
	}

	/**
	 * Converts a {@link TerminalForegroundColor} to a
	 * {@link TerminalBackgroundColor}
	 * 
	 * @param color
	 *            The {@link TerminalForegroundColor} to convert
	 */
	public static TerminalBackgroundColor fromForegroundColor(TerminalForegroundColor color) {
		return TerminalBackgroundColor.valueOf(color.toString());
	}
}
