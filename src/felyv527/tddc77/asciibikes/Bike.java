package felyv527.tddc77.asciibikes;

import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalColorMode;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.common.AsciiSprite;
import felyv527.tddc77.asciibikes.common.Direction;
import felyv527.tddc77.asciibikes.common.Point;
import felyv527.tddc77.asciibikes.common.Rectangle;
import felyv527.tddc77.asciibikes.debug.Console;
import felyv527.tddc77.asciibikes.io.TerminalInfo;

public class Bike implements Drawable, Collideable {
	private static final int UPDATE_INTERVAL = (int) (100f / TerminalInfo
			.getTerminalCols() * 100), UNLOAD_TIME = 3000;

	private BikeTrail trail;
	private Direction previousDirection, currentDirection;
	private int updateTime = 0, unloadTimer = 0;
	private boolean alive = true;

	private Point position;

	private final String[] spriteDown, spriteUp, spriteLeft, spriteRight;

	public Bike(TerminalForegroundColor bikeColor, Point startPoint,
			Direction startDirection, int trailLength) {
		this.position = startPoint;
		this.currentDirection = startDirection;
		this.previousDirection = currentDirection;

		trail = new BikeTrail(new Point(this.position), trailLength,
				TerminalBackgroundColor.fromForegroundColor(bikeColor));

		spriteDown = new String[] { Colorizer.colorize("\u2B19", bikeColor,
				UIInfo.arenaBackground, TerminalColorMode.BRIGHT) };
		spriteUp = new String[] { Colorizer.colorize("\u2B18", bikeColor,
				UIInfo.arenaBackground, TerminalColorMode.BRIGHT) };
		spriteLeft = new String[] { Colorizer.colorize("\u2B16", bikeColor,
				UIInfo.arenaBackground, TerminalColorMode.BRIGHT) };
		spriteRight = new String[] { Colorizer.colorize("\u2B17", bikeColor,
				UIInfo.arenaBackground, TerminalColorMode.BRIGHT) };

		DrawHandler.registerForDraw(this);
		CollisionHandler.registerForCollision(this);
	}

	public void turnLeft() {
		// Hindrar 180 graderssvängar in i egen vägg
		switch (previousDirection) {
		case DOWN:
			currentDirection = Direction.RIGHT;
			break;
		case LEFT:
			currentDirection = Direction.DOWN;
			break;
		case RIGHT:
			currentDirection = Direction.UP;
			break;
		case UP:
			currentDirection = Direction.LEFT;
			break;
		}
	}

	public void turnRight() {
		// Hindrar 180 graderssvängar
		switch (previousDirection) {
		case DOWN:
			currentDirection = Direction.LEFT;
			break;
		case LEFT:
			currentDirection = Direction.UP;
			break;
		case RIGHT:
			currentDirection = Direction.DOWN;
			break;
		case UP:
			currentDirection = Direction.RIGHT;
			break;
		}
	}

	public void update(long deltaMillis) {
		if (alive) {
			updateTime += deltaMillis;
			unloadTimer = 0;
		} else {
			updateTime = 0;
			unloadTimer += deltaMillis;
		}

		if (updateTime >= UPDATE_INTERVAL) {
			updateTime %= UPDATE_INTERVAL;

			previousDirection = currentDirection;

			trail.update(new Point(this.position));

			switch (currentDirection) {
			case DOWN:
				position.setY(position.getY() + 1);
				break;
			case LEFT:
				position.setX(position.getX() - 1);
				break;
			case RIGHT:
				position.setX(position.getX() + 1);
				break;
			case UP:
				position.setY(position.getY() - 1);
				break;
			}
		}

		if (unloadTimer >= UNLOAD_TIME) {
			this.unload();
		}
	}

	public boolean isAlive() {
		return this.alive;
	}

	public void unload() {
		this.trail.unload();
		CollisionHandler.unRegisterForCollision(this);
		DrawHandler.unRegisterForDraw(this);
	}

	@Override
	public AsciiSprite getAsciiSprite() {
		switch (previousDirection) {
		case DOWN:
			return new AsciiSprite(this.position, spriteDown);
		case LEFT:
			return new AsciiSprite(this.position, spriteLeft);
		case RIGHT:
			return new AsciiSprite(this.position, spriteRight);
		case UP:
			return new AsciiSprite(this.position, spriteUp);
		}
		return null;
	}

	@Override
	public Rectangle getCollisionRect() {
		return new Rectangle(this.position, 1, 1);
	}

	@Override
	public CollideableType getCollideableType() {
		return CollideableType.BIKE;
	}

	@Override
	public void onCollision(CollideableType collidedWithType) {
		Console.println("Ouch! A bike collided with a "
				+ collidedWithType.toString());
		alive = false;
		CollisionHandler.unRegisterForCollision(this);
	}
}
