package felyv527.tddc77.asciibikes.menu;

import java.util.Set;

import felyv527.tddc77.asciibikes.StringTools;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.colors.TerminalColorMode;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.game.scoring.ScoreManager;
import felyv527.tddc77.asciibikes.game.scoring.ScoreInfo;
import felyv527.tddc77.asciibikes.io.Keys;

/**
 * A menu showing the scoreboard after a game
 *
 */
public class ScoreboardMenu extends ButtonedMenu {

	/**
	 * Creates a menu for showing the scoreboard
	 * 
	 * @param title
	 *            The title of this scoreboard
	 */
	public ScoreboardMenu(String title) {
		super(title);
	}

	@Override
	protected void onPreShow() {
		super.onPreShow();

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
					(Menu.DESCRIPTION_WIDTH - maxNameLen - maxScoreLen % 2 == 0 ? "  " : "   "),
					standings[i].getScore());
			int len = representation[i].length();
			representation[i] = Colorizer.colorize(representation[i], TerminalForegroundColor.WHITE,
					standings[i].getBikeInfo().getBackgroundColor(), TerminalColorMode.BRIGHT);

			/*
			 * {@link StringTools#center} would normally be used here, but since
			 * the method adds trailing whitespace, that would mess up the
			 * colors.
			 */
			representation[i] = StringTools.repeat(' ', Menu.DESCRIPTION_WIDTH / 2 - len / 2) + representation[i];
		}

		this.setDescription(String.join("\n", representation));

	}

	@Override
	protected MenuResult onMenuUpdate(Set<Keys> pressedKeys) {
		return MenuResult.COMPLETED;
	}

	@Override
	protected void onPostShow() {
	}

	@Override
	public boolean isEnabled() {
		return ScoreManager.getStandings().length > 0;
	}

}
