package felyv527.tddc77.asciibikes.game.ui;

import felyv527.tddc77.asciibikes.Point;
import felyv527.tddc77.asciibikes.StringTools;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalColorMode;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.drawing.AsciiSprite;
import felyv527.tddc77.asciibikes.game.GameWindowAreas;

/**
 * A simple label showing the current round number in the statistics area of the
 * game.
 *
 */
public class RoundDisplay implements UiManagable {

	private int roundNumber = 0;

	private AsciiSprite sprite;

	@Override
	public AsciiSprite getAsciiSprite() {
		return sprite;
	}

	@Override
	public void onRoundStart() {
		roundNumber++;

		String[] representation = new String[] { " ", "Round " + roundNumber, " " };
		representation = StringTools.center(representation, GameWindowAreas.StatsArea.getWidth());
		representation = Colorizer.colorize(representation, TerminalForegroundColor.WHITE,
				TerminalBackgroundColor.BLACK, TerminalColorMode.BRIGHT);

		this.sprite = new AsciiSprite(
				new Point(GameWindowAreas.StatsArea.getLeft(), GameWindowAreas.StatsArea.getTop()), representation);
	}

	@Override
	public void update(long deltaMillis) {

	}

	@Override
	public void onRoundEnd() {
	}

}
