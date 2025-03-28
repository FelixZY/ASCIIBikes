package felyv527.tddc77.asciibikes.game.ui;

import felyv527.tddc77.asciibikes.Point;
import felyv527.tddc77.asciibikes.StringTools;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.drawing.AsciiSprite;
import felyv527.tddc77.asciibikes.font.HeadlineFont;
import felyv527.tddc77.asciibikes.game.GameWindowAreas;
import felyv527.tddc77.asciibikes.game.scoring.ScoreManager;
import felyv527.tddc77.asciibikes.game.scoring.ScoreInfo;

/**
 * Displays the current score in the statistics area of the game.
 *
 */
public class ScoreDisplay implements UiManagable {

	private static final Point screenLocation = new Point(GameWindowAreas.StatsArea.getLeft(),
			GameWindowAreas.StatsArea.getTop() + 5 + (new HeadlineFont()).applyFont("A").length);

	private static final int UPDATE_INTERVAL = 80;

	private int timer = 0;

	private AsciiSprite sprite = new AsciiSprite(screenLocation, new String[0]);

	@Override
	public void onRoundStart() {
		buildSprite();
	}

	@Override
	public void update(long deltaMillis) {
		timer += deltaMillis;

		if (timer >= UPDATE_INTERVAL) {
			timer %= UPDATE_INTERVAL;

			/*
			 * This class performs a lot of checks and work to build its sprite,
			 * which is completely unneccesary. Therefore we reduce the number
			 * of calls to {#buildSprite()} a bit.
			 */
			buildSprite();
		}
	}

	@Override
	public void onRoundEnd() {
		buildSprite();
	}

	@Override
	public AsciiSprite getAsciiSprite() {
		return sprite;
	}

	private void buildSprite() {
		ScoreInfo[] standings = ScoreManager.getStandings();

		// Calculate the length of the longest name and score
		int maxNameLen = -1, maxScoreLen = 1;

		for (ScoreInfo standing : standings) {
			maxNameLen = Math.max(standing.getBikeInfo().getName().length(), maxNameLen);
			maxScoreLen = Math.max(String.valueOf(standing.getScore()).length(), maxScoreLen);
		}

		String[] representation = new String[standings.length];

		/*
		 * Here we put names on the left and score on the right, padded with two
		 * spaces and colorize the resulting string with the player's selected
		 * color
		 */
		for (int i = 0; i < standings.length; i++) {
			representation[i] = String.format("  %-" + maxNameLen + "s%s%" + maxScoreLen + "s  ",
					standings[i].getBikeInfo().getName(),
					StringTools.repeat(' ', GameWindowAreas.StatsArea.getWidth() - maxNameLen - maxScoreLen - 4),
					standings[i].getScore());
			representation[i] = Colorizer.colorize(representation[i], standings[i].getBikeInfo().getBackgroundColor());
		}

		sprite = new AsciiSprite(screenLocation, representation);
	}

}
