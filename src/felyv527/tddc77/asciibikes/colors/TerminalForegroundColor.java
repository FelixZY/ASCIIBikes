package felyv527.tddc77.asciibikes.colors;

/**
 * Represents all possible foreground colors that can be used to color text in
 * the terminal.
 *
 */
public enum TerminalForegroundColor {
	/*
	 * There are more colors in most terminals, but these are the ones I got to
	 * work during development.
	 */

	BLACK("\u001B[30m"), RED("\u001B[31m"), GREEN("\u001B[32m"), YELLOW("\u001B[33m"), BLUE("\u001B[34m"),
	MAGENTA("\u001B[35m"), CYAN("\u001B[36m"), WHITE("\u001B[37m"), DEFAULT("\u001B[38m");

	final String escapeSequence;

	TerminalForegroundColor(String escapeSequence) {
		this.escapeSequence = escapeSequence;
	}

	/**
	 * Converts a {@link TerminalBackgroundColor} to a
	 * {@link TerminalForegroundColor}
	 * 
	 * @param color
	 *            The {@link TerminalBackgroundColor} to convert
	 */
	public static TerminalForegroundColor fromBackgroundColor(TerminalBackgroundColor color) {
		return TerminalForegroundColor.valueOf(color.toString());
	}
}
