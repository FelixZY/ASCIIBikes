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
 * A simple clock showing the number of seconds since round start in the
 * statistics area of the game.
 */
public class RoundTimer implements UiManagable {

	private long time = 0;

	@Override
	public void onRoundStart() {
		this.time = 0;
	}

	@Override
	public void update(long deltaTime) {
		this.time += deltaTime;
	}

	@Override
	public void onRoundEnd() {
	}

	@Override
	public AsciiSprite getAsciiSprite() {
		int seconds = (int) (time / 1000);

		String[] representation = (new HeadlineFont()).applyFont(String.format("%02d", seconds));

		representation = StringTools.center(representation, GameWindowAreas.StatsArea.getWidth());

		representation = Colorizer.colorize(representation, TerminalForegroundColor.BLACK,
				TerminalBackgroundColor.WHITE, TerminalColorMode.BRIGHT);

		return new AsciiSprite(new Point(GameWindowAreas.StatsArea.getLeft(), GameWindowAreas.StatsArea.getTop() + 4),
				representation);
	}

}
