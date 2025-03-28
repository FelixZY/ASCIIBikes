package felyv527.tddc77.asciibikes.io;

/**
 * {@code OutputManager} provides simple methods for writing to a terminal in
 * raw mode.
 */
public class OutputManager {

	private OutputManager() {
		// Utility class. Should not be instantiated.
	}

	/**
	 * Prints an object.
	 * 
	 * @param obj
	 */
	public static void print(Object obj) {
		System.out.print(obj);
	}

	/**
	 * Prints an object with a trailing newline and carriage return.
	 * 
	 * If {@code obj} is an instance of {@link CharSequence}, any contained
	 * newlines will be adjusted to contain carriage returns as well.
	 * 
	 * @param obj
	 */
	public static void println(Object obj) {
		if (obj instanceof CharSequence) {
			obj = ((CharSequence) obj).toString().replace("\n", "\n\r");
		}
		System.out.print(obj + "\n\r");
	}

	/**
	 * Clears the terminal screen
	 */
	public static void clear() {
		// Resets the caret position and clears the screen
		System.out.print("\033[H\033[J");
	}

}
