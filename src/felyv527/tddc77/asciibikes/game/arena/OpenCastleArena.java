package felyv527.tddc77.asciibikes.game.arena;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import felyv527.tddc77.asciibikes.Point;
import felyv527.tddc77.asciibikes.game.Direction;
import felyv527.tddc77.asciibikes.game.GameWindowAreas;

/**
 * An open arena consisting only of the standard 4 walls.
 * 
 * <p>
 * Suitable for a maximum of 8 players.
 */
public class OpenCastleArena extends Arena {

	private static final SpawnLocation[] spawnLocations;

	static {
		/*
		 * The {@link SpawnLocation}s will remain the same no matter the
		 * instance. Therefore we only need to generate them the first time this
		 * arena is used.
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

				List<Direction> availableDirs = new ArrayList<Direction>(4);

				if (x > 0)
					availableDirs.add(Direction.LEFT);

				if (y > 0)
					availableDirs.add(Direction.UP);

				if (x + 1 < maxX)
					availableDirs.add(Direction.RIGHT);

				if (y + 1 < maxY)
					availableDirs.add(Direction.DOWN);

				for (Direction dir : availableDirs)
					listLocations.add(new SpawnLocation(new Point(col, row), dir));
			}

		spawnLocations = listLocations.toArray(new SpawnLocation[listLocations.size()]);
	}

	@Override
	public SpawnLocation[] getSpawnLocations(int numPlayers) {
		return spawnLocations;
	}

}
