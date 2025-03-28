package felyv527.tddc77.asciibikes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * {@link TerminalDimensions} is responsible for retrieving and making the
 * initial terminal dimensions accessible.
 * 
 * Note that the dimensions contained within this class are not updated if the
 * terminal changes its initial size.
 *
 */
public class TerminalDimensions {

	private static final String[] getSizeCommand = new String[] { "/bin/sh", "-c", "stty size </dev/tty" };

	private static final int numColumns, numLines;

	static {
		String size = execCommand(getSizeCommand);
		numColumns = Integer.parseInt(size.split("\\s")[1]);
		numLines = Integer.parseInt(size.split("\\s")[0]);
	}

	/**
	 * Gets the number of columns in the current terminal.
	 * 
	 * <p>
	 * This is basically the terminal's width.
	 * 
	 * <p>
	 * Note that terminal columns are generally more numerous and not as wide as
	 * lines are tall.
	 */
	public static int getNumColumns() {
		return numColumns;
	}

	/**
	 * Gets the number of lines in the current terminal.
	 * 
	 * <p>
	 * This is basically the terminal's height.
	 * 
	 * <p>
	 * Note that terminal lines are generally fewer and taller than columns are
	 * wide.
	 */
	public static int getNumLines() {
		return numLines;
	}

	/**
	 * Executes a command in the system shell and returns the response string.
	 * 
	 * <p>
	 * Note that this method will only work for single line responses.
	 * 
	 * @param command
	 *            The command to execute in the system terminal
	 * @return The response string
	 */
	private static String execCommand(String[] command) {
		try {
			Process p = Runtime.getRuntime().exec(command);
			Reader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			p.waitFor();

			StringBuilder outputData = new StringBuilder();
			while (reader.ready()) {
				outputData.append((char) reader.read());
			}
			return outputData.toString().trim();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
