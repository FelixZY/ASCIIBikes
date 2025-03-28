package felyv527.tddc77.asciibikes.game.arena;

import java.util.ArrayList;
import java.util.List;

import felyv527.tddc77.asciibikes.Rectangle;
import felyv527.tddc77.asciibikes.collision.CollisionManager;
import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.drawing.DrawManager;
import felyv527.tddc77.asciibikes.game.GameWindowAreas;

/**
 * A collection of preset obstacles and available spawn locations
 *
 */
public abstract class Arena {

	/**
	 * The background used for open space in the arena
	 */
	public static final TerminalBackgroundColor arenaBackground = TerminalBackgroundColor.BLACK;

	private List<Obstacle> obstacles = new ArrayList<Obstacle>();

	protected Arena() {
		// All arenas need to be framed by a set of walls.
		addDefaultWalls();
	}

	protected void addObstacle(Obstacle obstacle) {
		this.obstacles.add(obstacle);
	}

	/**
	 * Loads and registers all obstacles in the arena for drawing and collision
	 * detection.
	 */
	public void loadArena() {
		for (Obstacle obstacle : obstacles) {
			DrawManager.registerForOverlayDraw(obstacle);
			CollisionManager.registerForCollision(obstacle);
		}
	}

	/**
	 * Returns available spawn locations in the current arena.
	 * 
	 * @param numPlayers
	 *            The number of players that will be spawned in this arena.
	 * @return An array of available spawnLocations, at least the size of
	 *         {@code numPlayers}
	 */
	public abstract SpawnLocation[] getSpawnLocations(int numPlayers);

	private void addDefaultWalls() {
		// Left wall
		this.obstacles.add(new StaticObstacle(new Rectangle(GameWindowAreas.GameArea.getLeft(),
				GameWindowAreas.GameArea.getTop(), 1, GameWindowAreas.GameArea.getHeight())));

		// Top wall
		this.obstacles.add(new StaticObstacle(new Rectangle(GameWindowAreas.GameArea.getLeft() + 1,
				GameWindowAreas.GameArea.getTop(), GameWindowAreas.GameArea.getWidth() - 2, 1)));

		// Right wall
		this.obstacles.add(new StaticObstacle(new Rectangle(GameWindowAreas.GameArea.getRight(),
				GameWindowAreas.GameArea.getTop(), 1, GameWindowAreas.GameArea.getHeight())));

		// Bottom wall
		this.obstacles.add(new StaticObstacle(new Rectangle(GameWindowAreas.GameArea.getLeft() + 1,
				GameWindowAreas.GameArea.getBottom(), GameWindowAreas.GameArea.getWidth() - 2, 1)));
	}
}
