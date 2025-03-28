package felyv527.tddc77.asciibikes.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

public class TerminalInfo {
	private static final String[] getSizeCommand = new String[] { "/bin/sh", "-c", "stty size </dev/tty" };

	private static int terminalCols, terminalLines;

	static {
		String size = execCommand(getSizeCommand);
		terminalCols = Integer.parseInt(size.split("\\s")[1]);
		terminalLines = Integer.parseInt(size.split("\\s")[0]);
	}

	public static int getTerminalCols() {
		return terminalCols;
	}

	public static int getTerminalLines() {
		return terminalLines;
	}

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
