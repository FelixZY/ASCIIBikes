package felyv527.tddc77.asciibikes.game.arena;

import felyv527.tddc77.asciibikes.Point;
import felyv527.tddc77.asciibikes.Rectangle;
import felyv527.tddc77.asciibikes.collision.CollideableType;
import felyv527.tddc77.asciibikes.drawing.AsciiSprite;

/**
 * A static obstacle that will not move or react to collisions.
 *
 */
public class StaticObstacle extends Obstacle {

	private Rectangle extent;
	private AsciiSprite sprite;

	/**
	 * Constructs and initializes an obstacle taking up the specified
	 * rectangular surface. This obstacle will not move or react to collisions
	 * 
	 * @param extent
	 *            The extent of this obstacle
	 */
	public StaticObstacle(Rectangle extent) {
		this.extent = extent;
		this.sprite = new AsciiSprite(new Point(extent.getX(), extent.getY()), this.getStringRepresentation(extent));
	}

	@Override
	public Rectangle getCollisionRect() {
		return extent;
	}

	@Override
	public AsciiSprite getAsciiSprite() {
		return sprite;
	}

	@Override
	public void onCollision(CollideableType collidedWithType) {
	}
}
