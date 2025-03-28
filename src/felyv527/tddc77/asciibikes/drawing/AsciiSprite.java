package felyv527.tddc77.asciibikes.drawing;

import felyv527.tddc77.asciibikes.Point;

/**
 * A simple sprite that can be drawn onto the terminal screen.
 * 
 * <p>
 * This class does not register itself with {@link DrawManager} but instances
 * can be registered by the instance holder.
 */
public class AsciiSprite implements Drawable {

	private final Point origin;
	private final String[] sprite;

	/**
	 * Constructs and initializes a sprite.
	 * 
	 * @param origin
	 *            A point representing the top left corner of this sprite from
	 *            which it will be drawn (inclusive)
	 * @param sprite
	 *            An ascii art representation of this sprite.
	 * 
	 *            <p>
	 *            This is what will actually be drawn to the screen.
	 * 
	 *            <p>
	 *            Every string in the array will be drawn on its own line.
	 */
	public AsciiSprite(Point origin, String[] sprite) {
		this.origin = origin;
		this.sprite = sprite;
	}

	/**
	 * Gets the point from which to start drawing this sprite
	 */
	public Point getSpriteOrigin() {
		return origin;
	}

	/**
	 * Gets the ascii representation of this sprite
	 */
	public String[] getStringRepresentation() {
		return sprite;
	}

	@Override
	public AsciiSprite getAsciiSprite() {
		return this;
	}

}
