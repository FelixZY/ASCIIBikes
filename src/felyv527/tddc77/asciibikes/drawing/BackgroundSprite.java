package felyv527.tddc77.asciibikes.drawing;

import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.Point;
import felyv527.tddc77.asciibikes.StringTools;
import felyv527.tddc77.asciibikes.TerminalDimensions;
import felyv527.tddc77.asciibikes.colors.Colorizer;

/**
 * A terminal-filling sprite of a single color.
 * 
 * <p>
 * Should be registered with {@link DrawManager#registerForDraw(Drawable)}
 * immediately after the call to {@link DrawManager#startSession()} to be used
 * as a backdrop.
 *
 */
public class BackgroundSprite extends AsciiSprite {

	private static String[] backdropArray;

	static {
		/*
		 * The ascii representation for this sprite is simply whitespace filling
		 * the entire terminal.
		 */
		backdropArray = new String[TerminalDimensions.getNumLines()];
		for (int i = 0; i < backdropArray.length; i++) {
			backdropArray[i] = StringTools.repeat(' ', TerminalDimensions.getNumColumns());
		}
	}

	/**
	 * Creates a new instance of {@link BackgroundSprite} with the specified
	 * backdrop color
	 * 
	 * @param backdropColor
	 *            The color to use as a backdrop
	 */
	public BackgroundSprite(TerminalBackgroundColor backdropColor) {
		/*
		 * The backdropArray is cloned here as it otherwise will suffer from
		 * issues caused by it being passed by reference
		 */
		super(new Point(0, 0), Colorizer.colorize(backdropArray.clone(), backdropColor));
	}

}
