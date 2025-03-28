package felyv527.tddc77.asciibikes.game;

import felyv527.tddc77.asciibikes.Rectangle;
import felyv527.tddc77.asciibikes.TerminalDimensions;

/**
 * {@link GameWindowAreas} contains information on the distribution of the
 * terminal screen while the game is running.
 *
 */
public class GameWindowAreas {
	/**
	 * The area of the terminal where the game takes place
	 */
	public static final Rectangle GameArea = new Rectangle(0, 0,
			(int) (TerminalDimensions.getNumColumns() * (TerminalDimensions.getNumColumns() > 89 ? .8f : .68f)),
			(int) (TerminalDimensions.getNumLines()));

	/**
	 * The area of the screen in which statistics etc. is shown.
	 */
	public static final Rectangle StatsArea = new Rectangle(GameArea.getRight() + 1, 0,
			TerminalDimensions.getNumColumns() - GameArea.getWidth(), GameArea.getHeight());

	private GameWindowAreas() {
		// Utility class. Should not be instantiated.
	}
}
