package felyv527.tddc77.asciibikes.collision;

import felyv527.tddc77.asciibikes.Rectangle;

/**
 * A {@link Collideable} represents an object that can collide with other
 * collideable objects.
 * 
 * <p>
 * All {@link Collideable}s have a rectangular extent and can be associated with
 * a {@link CollideableType}.
 *
 */
public interface Collideable {

	/**
	 * Returns a rectangle describing this {@link Collideable}'s position and
	 * size in the world.
	 */
	Rectangle getCollisionRect();

	/**
	 * Returns this Collideable's {@link CollideableType}.
	 */
	CollideableType getCollideableType();

	/**
	 * Called whenever this {@link Collideable} collides with another
	 * {@link Collideable}.
	 * 
	 * @param collidedWithType
	 *            The type of object this {@link Collideable} has collided with.
	 */
	void onCollision(CollideableType collidedWithType);
}
