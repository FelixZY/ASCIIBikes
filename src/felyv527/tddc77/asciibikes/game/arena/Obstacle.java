package felyv527.tddc77.asciibikes.game.arena;

import felyv527.tddc77.asciibikes.colors.TerminalColorMode;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.Rectangle;
import felyv527.tddc77.asciibikes.StringTools;
import felyv527.tddc77.asciibikes.collision.Collideable;
import felyv527.tddc77.asciibikes.collision.CollideableType;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.drawing.Drawable;

/**
 * An obstacle
 *
 */
public abstract class Obstacle implements Collideable, Drawable {

	private static final TerminalForegroundColor obstacleForeground = TerminalForegroundColor.BLACK;
	private static final TerminalBackgroundColor obstacleBackground = TerminalBackgroundColor.WHITE;

	/**
	 * Returns the string representation for a normal obstacle.
	 * 
	 * <p>
	 * This method will apply the standard obstacle coloring and looks to any
	 * {@link Rectangle} and return its string representation.
	 * 
	 * <p>
	 * Note that this representation will contain invisible characters.
	 * 
	 * @param obstacleExtent
	 * @return
	 */
	protected String[] getStringRepresentation(Rectangle obstacleExtent) {
		return Colorizer.colorize(StringTools.frame(obstacleExtent), obstacleForeground, obstacleBackground,
				TerminalColorMode.BRIGHT);
	}

	@Override
	public CollideableType getCollideableType() {
		return CollideableType.OBSTACLE;
	}

}
