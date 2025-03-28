package felyv527.tddc77.asciibikes;

/**
 * A point representing a location in (x, y) coordinate space, specified in
 * integer precision.
 */
public class Point {

	private int x, y;

	/**
	 * Constructs and initializes a point at the origin (0, 0) of the coordinate
	 * space.
	 */
	public Point() {
		set(0, 0);
	}

	/**
	 * Constructs and initializes a point at the specified (x, y) location in
	 * the coordinate space.
	 * 
	 * @param x
	 *            The X coordinate of the newly constructed Point
	 * @param y
	 *            The Y coordinate of the newlt constructed Point
	 */
	public Point(int x, int y) {
		set(x, y);
	}

	/**
	 * Constructs and initializes a point with the same location as the
	 * specified Point object.
	 * 
	 * @param point
	 *            A point
	 */
	public Point(Point point) {
		this.x = point.x;
		this.y = point.y;
	}

	/**
	 * Sets the location of this point to the specified integer coordinates
	 * 
	 * @param x
	 *            The X coordinate of the new location
	 * @param y
	 *            The Y coordinate of the new location
	 */
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			return ((Point) obj).x == x && ((Point) obj).y == y;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (this.x + this.y + 1) * 5 + this.y * 31;
	}

}