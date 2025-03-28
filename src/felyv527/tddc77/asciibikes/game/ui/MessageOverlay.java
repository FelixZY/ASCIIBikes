package felyv527.tddc77.asciibikes.game.ui;

import felyv527.tddc77.asciibikes.Point;
import felyv527.tddc77.asciibikes.StringTools;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalColorMode;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.drawing.AsciiSprite;
import felyv527.tddc77.asciibikes.font.HeadlineFont;
import felyv527.tddc77.asciibikes.game.GameWindowAreas;

/**
 * A time limited overlay fitted to {@link GameWindowAreas#GameArea} displaying
 * a short message until it is hidden.
 *
 */
public class MessageOverlay extends TimeLimitedOverlay {

	private AsciiSprite sprite;

	/**
	 * Constructs and initializes a new {@link MessageOverlay} with the
	 * specified message and default colors.
	 * 
	 * @param message
	 *            The message to show
	 */
	public MessageOverlay(String message) {
		this(message, TerminalForegroundColor.BLACK, TerminalBackgroundColor.WHITE);
	}

	/**
	 * Constructs and initializes a new {@link MessageOverlay} with the
	 * specified message and colors.
	 * 
	 * @param message
	 *            The message to show
	 * @param foregroundColor
	 *            The text color to use
	 * @param backgroundColor
	 *            The background color to use
	 */
	public MessageOverlay(String message, TerminalForegroundColor foregroundColor,
			TerminalBackgroundColor backgroundColor) {

		String[] representation = (new HeadlineFont()).applyFont(message);
		representation = StringTools.center(representation, GameWindowAreas.GameArea.getWidth() - 2);
		representation = Colorizer.colorize(representation, foregroundColor, backgroundColor, TerminalColorMode.BRIGHT);
		this.sprite = new AsciiSprite(
				new Point(GameWindowAreas.GameArea.getLeft() + 1, GameWindowAreas.GameArea.getTop()
						+ GameWindowAreas.GameArea.getHeight() / 2 - representation.length / 2),
				representation);
	}

	@Override
	public AsciiSprite getAsciiSprite() {
		return this.sprite;
	}

}
