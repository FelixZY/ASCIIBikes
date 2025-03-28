package felyv527.tddc77.asciibikes.common;

public class Point {
	private int x, y;

	public Point() {
		set(0, 0);
	}

	public Point(int x, int y) {
		set(x, y);
	}

	public Point(Point point) {
		this.x = point.x;
		this.y = point.y;
	}

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
		if(obj instanceof Point) {
			return ((Point)obj).x == x && ((Point)obj).y == y;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (this.x + this.y + 1)  * 5 + this.y * 7;
	}
	
	
}