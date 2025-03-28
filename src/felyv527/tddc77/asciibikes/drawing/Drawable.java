package felyv527.tddc77.asciibikes.drawing;

/**
 * Represents an object that can be drawn to screen by {@link DrawManager}
 *
 */
public interface Drawable {
	/**
	 * Returns the sprite representation of this {@link Drawable}.
	 */
	AsciiSprite getAsciiSprite();
}
