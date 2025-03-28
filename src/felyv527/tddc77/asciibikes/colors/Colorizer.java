package felyv527.tddc77.asciibikes.colors;

/**
 * Provides methods for adding terminal colors to strings.
 * 
 * <p>
 * Note that all methods in this class will add invisible characters around
 * their input string. This will make any measurements of the string's length
 * difficult to use and any such measurements should therefore be performed
 * before calling any method in this class.
 *
 */
public class Colorizer {

	// All methods in this class are basically overloads of the same two methods
	// at the end of the file.

	private static final String resetSequence = "\u001B[0m";

	/**
	 * Applies a color escape to the input string resulting in colored output
	 * when outputting to a terminal.
	 * 
	 * <p>
	 * Note that this method will add invisible characters around the input
	 * string. This will make any measurements of the string's length difficult
	 * to use and any such measurements should therefore be performed before
	 * calling this method.
	 * 
	 * @param str
	 *            The string to apply color to
	 * @param foreground
	 * @return The input string containing color escape codes
	 */
	public static String colorize(String str, TerminalForegroundColor foreground) {
		return colorize(str, foreground.escapeSequence);
	}

	/**
	 * Applies a color escape to the input string resulting in colored output
	 * when outputting to a terminal.
	 * 
	 * <p>
	 * Note that this method will add invisible characters around the input
	 * string. This will make any measurements of the string's length difficult
	 * to use and any such measurements should therefore be performed before
	 * calling this method.
	 * 
	 * @param str
	 *            The string to apply color to
	 * @param foreground
	 *            The foreground color to apply
	 * @param colorMode
	 *            The color mode to use
	 * @return The input string containing color escape codes
	 */
	public static String colorize(String str, TerminalForegroundColor foreground, TerminalColorMode colorMode) {
		return colorize(str, foreground.escapeSequence + colorMode.escapeSequence);
	}

	/**
	 * Applies a color escape to the input string resulting in colored output
	 * when outputting to a terminal.
	 * 
	 * <p>
	 * Note that this method will add invisible characters around the input
	 * string. This will make any measurements of the string's length difficult
	 * to use and any such measurements should therefore be performed before
	 * calling this method.
	 * 
	 * @param str
	 *            The string to apply color to
	 * @param background
	 *            The background color to apply
	 * @return The input string containing color escape codes
	 */
	public static String colorize(String str, TerminalBackgroundColor background) {
		return colorize(str, background.escapeSequence);
	}

	/**
	 * Applies a color escape to the input string resulting in colored output
	 * when outputting to a terminal.
	 * 
	 * <p>
	 * Note that this method will add invisible characters around the input
	 * string. This will make any measurements of the string's length difficult
	 * to use and any such measurements should therefore be performed before
	 * calling this method.
	 * 
	 * @param str
	 *            The string to apply color to
	 * @param background
	 *            The background color to apply
	 * @param colorMode
	 *            The color mode to use
	 * @return The input string containing color escape codes
	 */
	public static String colorize(String str, TerminalBackgroundColor background, TerminalColorMode colorMode) {
		return colorize(str, background.escapeSequence + colorMode.escapeSequence);
	}

	/**
	 * Applies a color escape to the input string resulting in colored output
	 * when outputting to a terminal.
	 * 
	 * <p>
	 * Note that this method will add invisible characters around the input
	 * string. This will make any measurements of the string's length difficult
	 * to use and any such measurements should therefore be performed before
	 * calling this method.
	 * 
	 * @param str
	 *            The string to apply color to
	 * @param foreground
	 *            The foreground color to apply
	 * @param background
	 *            The background color to apply
	 * @return The input string containing color escape codes
	 */
	public static String colorize(String str, TerminalForegroundColor foreground, TerminalBackgroundColor background) {
		return colorize(str, foreground.escapeSequence + background.escapeSequence);
	}

	/**
	 * Applies a color escape to the input string resulting in colored output
	 * when outputting to a terminal.
	 * 
	 * <p>
	 * Note that this method will add invisible characters around the input
	 * string. This will make any measurements of the string's length difficult
	 * to use and any such measurements should therefore be performed before
	 * calling this method.
	 * 
	 * @param str
	 *            The string to apply color to
	 * @param foreground
	 *            The foreground color to apply
	 * @param background
	 *            The background color to apply
	 * @param colorMode
	 *            The color mode to use
	 * @return The input string containing color escape codes
	 */
	public static String colorize(String str, TerminalForegroundColor foreground, TerminalBackgroundColor background,
			TerminalColorMode colorMode) {
		return colorize(str, foreground.escapeSequence + background.escapeSequence + colorMode.escapeSequence);
	}

	/**
	 * Applies a color escape to all strings in the input string array resulting
	 * in colored output when outputting to a terminal.
	 * 
	 * <p>
	 * Note that this method will add invisible characters around the input
	 * strings. This will make any measurements of the strings' length difficult
	 * to use and any such measurements should therefore be performed before
	 * calling this method.
	 * 
	 * <p>
	 * The array itself will retain it's original length.
	 * 
	 * @param str_arr
	 *            The string array to apply color to
	 * @param foreground
	 *            The foreground color to apply
	 * @return The input string array containing color escape codes
	 */
	public static String[] colorize(String[] str_arr, TerminalForegroundColor foreground) {
		return colorize(str_arr, foreground.escapeSequence);
	}

	/**
	 * Applies a color escape to all strings in the input string array resulting
	 * in colored output when outputting to a terminal.
	 * 
	 * <p>
	 * Note that this method will add invisible characters around the input
	 * strings. This will make any measurements of the strings' length difficult
	 * to use and any such measurements should therefore be performed before
	 * calling this method.
	 * 
	 * <p>
	 * The array itself will retain it's original length.
	 * 
	 * @param str_arr
	 *            The string array to apply color to
	 * @param foreground
	 *            The foreground color to apply
	 * @param colorMode
	 *            The color mode to use
	 * @return The input string array containing color escape codes
	 */
	public static String[] colorize(String[] str_arr, TerminalForegroundColor foreground, TerminalColorMode colorMode) {
		return colorize(str_arr, foreground.escapeSequence + colorMode.escapeSequence);
	}

	/**
	 * Applies a color escape to all strings in the input string array resulting
	 * in colored output when outputting to a terminal.
	 * 
	 * <p>
	 * Note that this method will add invisible characters around the input
	 * strings. This will make any measurements of the strings' length difficult
	 * to use and any such measurements should therefore be performed before
	 * calling this method.
	 * 
	 * <p>
	 * The array itself will retain it's original length.
	 * 
	 * @param str_arr
	 *            The string array to apply color to
	 * @param background
	 *            The background color to apply
	 * @return The input string array containing color escape codes
	 */
	public static String[] colorize(String[] str_arr, TerminalBackgroundColor background) {
		return colorize(str_arr, background.escapeSequence);
	}

	/**
	 * Applies a color escape to all strings in the input string array resulting
	 * in colored output when outputting to a terminal.
	 * 
	 * <p>
	 * Note that this method will add invisible characters around the input
	 * strings. This will make any measurements of the strings' length difficult
	 * to use and any such measurements should therefore be performed before
	 * calling this method.
	 * 
	 * <p>
	 * The array itself will retain it's original length.
	 * 
	 * @param str_arr
	 *            The string array to apply color to
	 * @param background
	 *            The background color to apply
	 * @param colorMode
	 *            The color mode to use
	 * @return The input string array containing color escape codes
	 */
	public static String[] colorize(String[] str_arr, TerminalBackgroundColor background, TerminalColorMode colorMode) {
		return colorize(str_arr, background.escapeSequence + colorMode.escapeSequence);
	}

	/**
	 * Applies a color escape to all strings in the input string array resulting
	 * in colored output when outputting to a terminal.
	 * 
	 * <p>
	 * Note that this method will add invisible characters around the input
	 * strings. This will make any measurements of the strings' length difficult
	 * to use and any such measurements should therefore be performed before
	 * calling this method.
	 * 
	 * <p>
	 * The array itself will retain it's original length.
	 * 
	 * @param str_arr
	 *            The string array to apply color to
	 * @param foreground
	 *            The foreground color to apply
	 * @param background
	 *            The background color to apply
	 * @return The input string array containing color escape codes
	 */
	public static String[] colorize(String[] str_arr, TerminalForegroundColor foreground,
			TerminalBackgroundColor background) {
		return colorize(str_arr, foreground.escapeSequence + background.escapeSequence);
	}

	/**
	 * Applies a color escape to all strings in the input string array resulting
	 * in colored output when outputting to a terminal.
	 * 
	 * <p>
	 * Note that this method will add invisible characters around the input
	 * strings. This will make any measurements of the strings' length difficult
	 * to use and any such measurements should therefore be performed before
	 * calling this method.
	 * 
	 * <p>
	 * The array itself will retain it's original length.
	 * 
	 * @param str_arr
	 *            The string array to apply color to
	 * @param foreground
	 *            The foreground color to apply
	 * @param background
	 *            The background color to apply
	 * @param colorMode
	 *            The color mode to use
	 * @return The input string array containing color escape codes
	 */
	public static String[] colorize(String[] str_arr, TerminalForegroundColor foreground,
			TerminalBackgroundColor background, TerminalColorMode colorMode) {
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
