package felyv527.tddc77.asciibikes.game.arena;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import felyv527.tddc77.asciibikes.Point;
import felyv527.tddc77.asciibikes.Rectangle;
import felyv527.tddc77.asciibikes.game.Direction;
import felyv527.tddc77.asciibikes.game.GameWindowAreas;

/**
 * An arena with three large horizontally stacked pillars in the middle.
 * 
 * <p>
 * Suitable for a maximum of 8 players
 *
 */
public class ThreePillarsArena extends Arena {
	private static final int pillarWidth = GameWindowAreas.GameArea.getWidth() / 8;
	private static final int pillarHeight = GameWindowAreas.GameArea.getHeight() / 6;
	private static final Point pillarStart = new Point(
			GameWindowAreas.GameArea.getX() + GameWindowAreas.GameArea.getWidth() / 3,
			GameWindowAreas.GameArea.getY() + GameWindowAreas.GameArea.getHeight() / 2 - pillarHeight / 2);

	private static SpawnLocation[] spawnLocations;

	static {
		/*
		 * The {@link SpawnLocation}s will remain the same no matter the
		 * instance. Therefore we only need to generate them the first time this
		 * arena is used.
		 * 
		 * The logic is very similar to OpenCastles but a few spawnable
		 * directions have been removed, unfortunately forcing a code block
		 * copy.
		 */

		List<SpawnLocation> listLocations = new LinkedList<SpawnLocation>();

		int maxX = GameWindowAreas.GameArea.getWidth() > 130 ? 5 : 4;
		int maxY = GameWindowAreas.GameArea.getHeight() > 40 ? 4 : 2;

		for (int x = 0; x < maxX; x++)
			for (int y = 0; y < maxY; y++) {
				int col = GameWindowAreas.GameArea.getLeft() + GameWindowAreas.GameArea.getWidth() * (x + 1) / maxX
						- (int) Math.ceil(GameWindowAreas.GameArea.getWidth() / (2f * maxX));
				int row = GameWindowAreas.GameArea.getTop() + GameWindowAreas.GameArea.getHeight() * (y + 1) / maxY
						- (int) Math.ceil(GameWindowAreas.GameArea.getHeight() / (2f * maxY));

				List<Direction> availableDirs = new ArrayList<Direction>(3);

				/*
				 * When spawning near the pillars on large terminals we want
				 * some extra padding
				 */
				if (maxY == 4 && y == 1) {
					// Spawn is right above the pillars
					row -= 3;
				} else if (maxY == 4 && y == 2) {
					// Spawn is right below the pillars
					row += 3;
				}

				if (x > 0)
					availableDirs.add(Direction.LEFT);

				if (maxY == 4 && (y == 1 || y == 3)) {
					availableDirs.add(Direction.UP);
				}

				if (x + 1 < maxX)
					availableDirs.add(Direction.RIGHT);

				if (maxY == 4 && (y == 0 || y == 2))
					availableDirs.add(Direction.DOWN);

				for (Direction dir : availableDirs)
					listLocations.add(new SpawnLocation(new Point(col, row), dir));
			}

		spawnLocations = listLocations.toArray(new SpawnLocation[listLocations.size()]);
	}

	public ThreePillarsArena() {
		// Adds the three pillars to this arena.
		// This can only be done in an instance.
		for (int i = 1; i <= 3; i++)
			this.addObstacle(new StaticObstacle(
					new Rectangle(pillarStart.getX() * i - GameWindowAreas.GameArea.getWidth() / 6 - pillarWidth / 2,
							pillarStart.getY(), pillarWidth, pillarHeight)));
	}

	@Override
	public SpawnLocation[] getSpawnLocations(int numPlayers) {
		return spawnLocations;
	}
}
