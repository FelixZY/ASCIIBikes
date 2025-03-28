package felyv527.tddc77.asciibikes.common;

public class Rectangle {
	private int x = 0, y = 0, height = 0, width = 0;

	public Rectangle() {

	}

	public Rectangle(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	
	public Rectangle(Point origin, int width, int height) {
		this(origin.getX(), origin.getY(), width, height);
	}
	
	public Rectangle(Point origin, Point size) {
		this(origin.getX(), origin.getY(), size.getX(), size.getY());
	}

	public boolean intersects(Rectangle rect) {
		return !(this.getTop() > rect.getBottom() || this.getBottom() < rect.getTop() || this.getRight() < rect.getLeft()
				|| this.getLeft() > rect.getRight());
	}

	@Override
	public String toString() {
		return "Top: " + this.getTop() + " | Right: " + this.getRight() + " | Bottom: " + this.getBottom() + " | Left: "
				+ this.getLeft();
	}

	public int getTop() {
		return y;
	}

	public int getLeft() {
		return x;
	}

	public int getRight() {
		return x + width - 1;
	}

	public int getBottom() {
		return y + height - 1;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

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
