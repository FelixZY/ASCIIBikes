package felyv527.tddc77.asciibikes.colors;

public enum TerminalForegroundColor {

	BLACK("\u001B[30m"), RED("\u001B[31m"), GREEN("\u001B[32m"), YELLOW("\u001B[33m"), BLUE("\u001B[34m"), MAGENTA(
			"\u001B[35m"), CYAN("\u001B[36m"), WHITE("\u001B[37m"), DEFAULT("\u001B[38m");

	final String escapeSequence;

	TerminalForegroundColor(String escapeSequence) {
		this.escapeSequence = escapeSequence;
	}

	public static TerminalForegroundColor fromBackgroundColor(TerminalBackgroundColor color) {
		return TerminalForegroundColor.valueOf(color.toString());
	}
}
