package felyv527.tddc77.asciibikes.colors;

/**
 * Represents the color mode to use when coloring text in the terminal.
 *
 */
public enum TerminalColorMode {
	NORMAL(""), BRIGHT("\u001B[1m");

	final String escapeSequence;

	TerminalColorMode(String escapeSequence) {
		this.escapeSequence = escapeSequence;
	}
}
