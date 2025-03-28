package felyv527.tddc77.asciibikes.game.bike;

import java.util.Deque;
import java.util.LinkedList;

import felyv527.tddc77.asciibikes.Point;
import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.game.Direction;

class TrailManager {
	private Deque<TrailSection> trailSections = new LinkedList<>();
	private int maxTrailLength;
	private TerminalBackgroundColor trailColor;
	private Point lastSpawnPoint = new Point(-1, -1);

	TrailManager(int maxTrailLength, TerminalBackgroundColor trailColor) {
		this.maxTrailLength = maxTrailLength;
		this.trailColor = trailColor;
	}

	void spawnTrailAt(Point point) {
		if (!point.equals(lastSpawnPoint)) {
			Direction spawnDirection = getSpawnDirection(point);
			if (!trailSections.isEmpty() && spawnDirection == trailSections.getLast().growDirection) {
				trailSections.getLast().expand();
			} else {
				trailSections.offer(new TrailSection(point, spawnDirection, trailColor));
			}

			shrinkTrail(length() - maxTrailLength);

			lastSpawnPoint = point;
		}
	}

	void shrinkTrail(int amount) {
		amount = Math.max(Math.min(amount, this.length()), 0);
		for (int i = 0; i < amount; i++) {
			trailSections.peek().shrink();
			if (trailSections.peek().length() == 0)
				trailSections.pop().unload();
		}
	}

	int length() {
		int length = 0;
		for (TrailSection trail : trailSections)
			length += trail.length();
		return length;
	}

	void removeTrail() {
		for (TrailSection trail : trailSections) {
			trail.unload();
		}
		trailSections.clear();
	}

	private Direction getSpawnDirection(Point spawnPoint) {
		if (spawnPoint.equals(new Point(lastSpawnPoint.getX(), lastSpawnPoint.getY() + 1)))
			return Direction.DOWN;
		else if (spawnPoint.equals(new Point(lastSpawnPoint.getX(), lastSpawnPoint.getY() - 1)))
			return Direction.UP;
		else if (spawnPoint.equals(new Point(lastSpawnPoint.getX() + 1, lastSpawnPoint.getY())))
			return Direction.RIGHT;
		else if (spawnPoint.equals(new Point(lastSpawnPoint.getX() - 1, lastSpawnPoint.getY())))
			return Direction.LEFT;
		else
			return null;
	}
}
