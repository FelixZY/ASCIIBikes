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
 * A time limited overlay fitted to {@link GameWindowAreas#GameArea} counting
 * down the seconds until it is hidden.
 *
 */
public class CountDownOverlay extends TimeLimitedOverlay {

	private static final Point screenLocation = new Point(
			new Point(GameWindowAreas.GameArea.getLeft() + 1, GameWindowAreas.GameArea.getTop()
					+ GameWindowAreas.GameArea.getHeight() / 2 - (new HeadlineFont()).applyFont("A").length / 2));

	@Override
	public AsciiSprite getAsciiSprite() {
		return new AsciiSprite(screenLocation, getStringRepresentation());
	}

	private String[] getStringRepresentation() {
		String[] representation = (new HeadlineFont())
				.applyFont(String.valueOf((int) Math.ceil((this.getRemainingMillis()) / 1000f)));
		representation = StringTools.center(representation, GameWindowAreas.GameArea.getWidth() - 2);
		representation = Colorizer.colorize(representation, TerminalForegroundColor.BLACK,
				TerminalBackgroundColor.WHITE, TerminalColorMode.BRIGHT);
		return representation;
	}

}
