package felyv527.tddc77.asciibikes.game.arena;

import felyv527.tddc77.asciibikes.Point;
import felyv527.tddc77.asciibikes.game.Direction;

/**
 * Represents a single point and direction available for spawn.
 *
 */
public class SpawnLocation {
	public final Point startPoint;
	public final Direction startDirection;

	/**
	 * Initializes a new SpawnLocation containing a point and direction
	 * available for spawn.
	 * 
	 * @param startPoint
	 *            The point representing a spawnable world location
	 * @param startDirection
	 *            The direction a spawning object should face
	 */
	public SpawnLocation(Point startPoint, Direction startDirection) {
		this.startPoint = startPoint;
		this.startDirection = startDirection;
	}
}
