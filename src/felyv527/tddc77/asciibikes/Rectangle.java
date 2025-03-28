package felyv527.tddc77.asciibikes;

/**
 * A representation of the extent of a rectangular entity without rotation.
 */
public class Rectangle {

	private int x = 0, y = 0, height = 0, width = 0;

	/**
	 * Constructs and initializes a rectangle at the origin (0, 0) with a width
	 * and height of 0
	 */
	public Rectangle() {

	}

	/**
	 * Constructs and initializes a rectangle at the origin (x, y) with the
	 * specified width and height
	 * 
	 * @param x
	 *            The X coordinate of the newly constructed rectangle
	 * @param y
	 *            The Y coordinate of the newly constructed rectangle
	 * @param width
	 *            The width of the newly constructed rectangle
	 * @param height
	 *            The height of the newly constructed rectangle
	 */
	public Rectangle(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}

	/**
	 * Constructs and initializes a rectangle originating at the point
	 * {@code origin] with the
	 * specified width and height
	 * 
	 * @param origin
	 *            The coordinates of the newly constructed rectangle
	 * @param width
	 *            The width of the newly constructed rectangle
	 * @param height
	 *            The height of the newly constructed rectangle
	 */
	public Rectangle(Point origin, int width, int height) {
		this(origin.getX(), origin.getY(), width, height);
	}

	/**
	 * Determines whether a specified rectangle intersects with this rectangle
	 * 
	 * @param rect
	 *            The rectangle to evaluate
	 * @return {@code true} if the specified rectagle intersects with this one;
	 *         {@code false} otherwise.
	 */
	public boolean intersects(Rectangle rect) {
		return !(this.getTop() > rect.getBottom() || this.getBottom() < rect.getTop()
				|| this.getRight() < rect.getLeft() || this.getLeft() > rect.getRight());
	}

	public int getArea() {
		return this.height * this.width;
	}

	/**
	 * Gets the Y coordinate of the top edge of this rectangle (inclusive)
	 */
	public int getTop() {
		return y;
	}

	/**
	 * Gets the X coordinate of the left edge of this rectangle (inclusive)
	 */
	public int getLeft() {
		return x;
	}

	/**
	 * Gets the X coordinate of the right edge of this rectangle (inclusive)
	 */
	public int getRight() {
		return x + width - 1;
	}

	/**
	 * Gets the Y coordinate of the bottom edge of this rectangle (inclusive)
	 */
	public int getBottom() {
		return y + height - 1;
	}

	/**
	 * Gets the X coordinate of this rectangle's origin (inclusive)
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the X coordinate of this rectangle's origin (inclusive)
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the Y coordinate of this rectangle's origin (inclusive)
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the Y coordinate of this rectangle's origin (inclusive)
	 */
	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height of this rectangle.
	 * 
	 * <p>
	 * If {@literal height < 0} the height will be set to 0
	 */
	public void setHeight(int height) {
		this.height = Math.max(height, 0);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = Math.max(width, 0);
	}
}
