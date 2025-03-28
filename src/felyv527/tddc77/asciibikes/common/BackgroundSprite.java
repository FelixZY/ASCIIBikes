package felyv527.tddc77.asciibikes.common;

import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.io.TerminalInfo;

public class BackgroundSprite extends AsciiSprite {

	private static String[] backgroundArray;

	static {
		backgroundArray = new String[TerminalInfo.getTerminalLines()];
		for (int i = 0; i < backgroundArray.length; i++) {
			backgroundArray[i] = StringTools.repeat(" ", TerminalInfo.getTerminalCols());
		}
	}

	public BackgroundSprite(TerminalBackgroundColor backgroundColor) {
		/*
		 * Observera att utan clone sparas även färgen till background array.
		 * Pass by reference...
		 */
		super(new Point(0, 0), Colorizer.colorize(backgroundArray.clone(), backgroundColor));
	}

}
