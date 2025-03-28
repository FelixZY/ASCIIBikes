package felyv527.tddc77.asciibikes.colors;

public enum TerminalBackgroundColor {

	BLACK("\u001B[40m"), RED("\u001B[41m"), GREEN("\u001B[42m"), YELLOW("\u001B[43m"), BLUE("\u001B[44m"), MAGENTA(
			"\u001B[45m"), CYAN("\u001B[44m"), WHITE("\u001B[47m"), DEFAULT("\u001B[48m");

	final String escapeSequence;

	TerminalBackgroundColor(String escapeSequence) {
		this.escapeSequence = escapeSequence;
	}
	
	public static TerminalBackgroundColor fromForegroundColor(TerminalForegroundColor color) {
		return TerminalBackgroundColor.valueOf(color.toString());
	}
}
