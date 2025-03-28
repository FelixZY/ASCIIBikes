package felyv527.tddc77.asciibikes.colors;

public class Colorizer {

	private static final String resetSequence = "\u001B[0m";

	public static String colorize(String str, TerminalForegroundColor foreground) {
		return colorize(str, foreground.escapeSequence);
	}

	public static String colorize(String str, TerminalForegroundColor foreground, TerminalColorMode colorMode) {
		return colorize(str, foreground.escapeSequence + colorMode.escapeSequence);
	}

	public static String colorize(String str, TerminalBackgroundColor background) {
		return colorize(str, background.escapeSequence);
	}

	public static String colorize(String str, TerminalBackgroundColor background, TerminalColorMode colorMode) {
		return colorize(str, background.escapeSequence + colorMode.escapeSequence);
	}

	public static String colorize(String str, TerminalForegroundColor foreground, TerminalBackgroundColor background) {
		return colorize(str, foreground.escapeSequence + background.escapeSequence);
	}

	public static String colorize(String str, TerminalForegroundColor foreground, TerminalBackgroundColor background,
			TerminalColorMode colorMode) {
		return colorize(str, foreground.escapeSequence + background.escapeSequence + colorMode.escapeSequence);
	}

	public static String[] colorize(String[] str_arr, TerminalForegroundColor foreground) {
		return colorize(str_arr, foreground.escapeSequence);
	}

	public static String[] colorize(String[] str_arr, TerminalForegroundColor foreground, TerminalColorMode colorMode) {
		return colorize(str_arr, foreground.escapeSequence + colorMode.escapeSequence);
	}

	public static String[] colorize(String[] str_arr, TerminalBackgroundColor background) {
		return colorize(str_arr, background.escapeSequence);
	}

	public static String[] colorize(String[] str_arr, TerminalBackgroundColor background, TerminalColorMode colorMode) {
		return colorize(str_arr, background.escapeSequence + colorMode.escapeSequence);
	}

	public static String[] colorize(String[] str_arr, TerminalForegroundColor foreground, TerminalBackgroundColor background) {
		return colorize(str_arr, foreground.escapeSequence + background.escapeSequence);
	}

	public static String[] colorize(String[] str_arr, TerminalForegroundColor foreground, TerminalBackgroundColor background,
			TerminalColorMode colorMode) {
		return colorize(str_arr, foreground.escapeSequence + background.escapeSequence + colorMode.escapeSequence);
	}

	private static String[] colorize(String[] str_arr, String escapeSequence) {
		if (str_arr.length > 0)
			for (int i = 0; i < str_arr.length; i++)
				str_arr[i] = colorize(str_arr[i], escapeSequence);

		return str_arr;
	}

	private static String colorize(String str, String escapeSequence) {
		return escapeSequence + str + resetSequence;
	}

}
