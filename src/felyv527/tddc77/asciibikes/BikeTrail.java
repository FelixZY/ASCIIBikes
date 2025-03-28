package felyv527.tddc77.asciibikes;

import java.util.Deque;
import java.util.LinkedList;
import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.common.AsciiSprite;
import felyv527.tddc77.asciibikes.common.Direction;
import felyv527.tddc77.asciibikes.common.Point;
import felyv527.tddc77.asciibikes.common.Rectangle;
import felyv527.tddc77.asciibikes.common.StringTools;

public class BikeTrail {
	private Deque<Trail> trailList = new LinkedList<>();
	private int maxTrailLength;
	private TerminalBackgroundColor trailColor;
	private Point lastSpawnPoint = new Point(-1, -1);

	public BikeTrail(Point startSpawnPoint, int maxTrailLength,
			TerminalBackgroundColor trailColor) {
		this.lastSpawnPoint = startSpawnPoint;
		this.maxTrailLength = maxTrailLength;
		this.trailColor = trailColor;
	}

	public void update(Point spawnPoint) {
		if (!spawnPoint.equals(lastSpawnPoint)) {
			Direction spawnDirection = getSpawnDirection(spawnPoint);
			if (!trailList.isEmpty()
					&& spawnDirection == trailList.getLast().growDirection) {
				trailList.getLast().expand();
			} else {
				trailList.offer(new Trail(spawnPoint, spawnDirection,
						trailColor));
			}

			if (length() > maxTrailLength) {
				trailList.peek().shrink();
				if (trailList.peek().length() == 0)
					trailList.pop().unload();
			}

			lastSpawnPoint = spawnPoint;
		}
	}

	public int length() {
		int length = 0;
		for (Trail trail : trailList)
			length += trail.length();
		return length;
	}

	private Direction getSpawnDirection(Point spawnPoint) {
		if (spawnPoint.equals(new Point(lastSpawnPoint.getX(), lastSpawnPoint
				.getY() + 1)))
			return Direction.DOWN;
		else if (spawnPoint.equals(new Point(lastSpawnPoint.getX(),
				lastSpawnPoint.getY() - 1)))
			return Direction.UP;
		else if (spawnPoint.equals(new Point(lastSpawnPoint.getX() + 1,
				lastSpawnPoint.getY())))
			return Direction.RIGHT;
		else
			return Direction.LEFT;
	}

	public void unload() {
		for (Trail trail : trailList) {
			trail.unload();
		}
	}

	private class Trail implements Drawable, Collideable {

		private Point origin;
		public final Direction growDirection;
		private String[] sprite;
		private int trailLength = 0;

		public Trail(Point origin, Direction growDirection,
				TerminalBackgroundColor trailColor) {
			this.origin = origin;
			this.growDirection = growDirection;
			updateTrailLength(1);
			DrawHandler.registerForDraw(this);
			CollisionHandler.registerForCollision(this);
		}

		public void expand() {
			if (growDirection == Direction.LEFT)
				origin.setX(origin.getX() - 1);
			else if (growDirection == Direction.UP)
				origin.setY(origin.getY() - 1);
			updateTrailLength(length() + 1);
		}

		public int length() {
			return trailLength;
		}

		private void updateTrailLength(int length) {
			trailLength = length;
			if (growDirection == Direction.LEFT
					|| growDirection == Direction.RIGHT) {
				this.sprite = new String[] { Colorizer.colorize(
						StringTools.repeat(" ", trailLength), trailColor) };
			} else {
				this.sprite = new String[trailLength];
				String colorized = Colorizer.colorize(" ", trailColor);

				for (int i = 0; i < sprite.length; i++) {
					sprite[i] = colorized;
				}
			}
		}

		public void shrink() {
			if (length() > 0) {
				if (growDirection == Direction.DOWN)
					origin.setY(origin.getY() + 1);
				else if (growDirection == Direction.RIGHT)
					origin.setX(origin.getX() + 1);

				updateTrailLength(length() - 1);
			}
		}

		public void unload() {
			DrawHandler.unRegisterForDraw(this);
			CollisionHandler.unRegisterForCollision(this);
		}

		@Override
		public AsciiSprite getAsciiSprite() {
			return new AsciiSprite(origin, sprite);
		}

		@Override
		public Rectangle getCollisionRect() {
			if (growDirection == Direction.UP
					|| growDirection == Direction.DOWN)
				return new Rectangle(origin.getX(), origin.getY(), 1,
						this.length());
			else
				return new Rectangle(origin.getX(), origin.getY(),
						this.length(), 1);
		}

		@Override
		public CollideableType getCollideableType() {
			return CollideableType.WALL;
		}

		@Override
		public void onCollision(CollideableType collidedWithType) {
		}

	}
}
