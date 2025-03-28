package felyv527.tddc77.asciibikes.game.bike;

import felyv527.tddc77.asciibikes.colors.TerminalColorMode;
import java.util.Set;

import felyv527.tddc77.asciibikes.Point;
import felyv527.tddc77.asciibikes.Rectangle;
import felyv527.tddc77.asciibikes.TerminalDimensions;
import felyv527.tddc77.asciibikes.collision.Collideable;
import felyv527.tddc77.asciibikes.collision.CollideableType;
import felyv527.tddc77.asciibikes.collision.CollisionManager;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.drawing.AsciiSprite;
import felyv527.tddc77.asciibikes.drawing.DrawManager;
import felyv527.tddc77.asciibikes.drawing.Drawable;
import felyv527.tddc77.asciibikes.game.Direction;
import felyv527.tddc77.asciibikes.game.arena.Arena;
import felyv527.tddc77.asciibikes.game.arena.SpawnLocation;
import felyv527.tddc77.asciibikes.game.config.BikeInfo;
import felyv527.tddc77.asciibikes.game.scoring.ScoreManager;
import felyv527.tddc77.asciibikes.io.Keys;

/**
 * The main player character, leaving a trail behind it as it speeds around.
 *
 */
public abstract class Bike implements Drawable, Collideable {
	private static final int UPDATE_INTERVAL = (int) (70f / TerminalDimensions.getNumColumns() * 100);

	/**
	 * In order to give the players time to orient themselves on round start,
	 * all bikes moves significantly slower for {@code SLOW_START_TIMEOUT}
	 * milliseconds
	 */
	private static final float SLOW_START_TIMEOUT = 1175;

	/**
	 * All bikes slowly accelerate until a maximum speed is reached after
	 * {@code TIME_TO_FULL_SPEED} milliseconds.
	 */
	private static final float TIME_TO_FULL_SPEED = 30000;

	/**
	 * This value determines the initial percentage of speed given to all bikes.
	 * 
	 * <p>
	 * It is from this initial percentage the bike will accelerate for
	 * {@code TIME_TO_FULL_SPEED} milliseconds.
	 */
	private static final float INITIAL_SPEED_MODIFIER = .5f;

	private static float speedModifier = INITIAL_SPEED_MODIFIER;

	private TrailManager trail;
	private Direction previousDirection, currentDirection;
	private int playerIndex, updateTime = 0;
	private long aliveTime = 0;
	private boolean alive = true;

	private Point position;

	private final String[] spriteDown, spriteUp, spriteLeft, spriteRight;

	/**
	 * Constructs and initializes a new {@link Bike} with the specified
	 * {@code bikeId} and {@code bikeInfo}
	 * 
	 * @param playerIndex
	 *            The index of the player controlling this bike.
	 * @param bikeInfo
	 *            Basic information about this bike
	 */
	public Bike(int playerIndex, BikeInfo bikeInfo) {
		this.playerIndex = playerIndex;

		trail = new TrailManager(bikeInfo.getTrailLength(), bikeInfo.getBackgroundColor());

		spriteDown = new String[] { Colorizer.colorize("\u2B19", bikeInfo.getForegroundColor(), Arena.arenaBackground,
				TerminalColorMode.BRIGHT) };
		spriteUp = new String[] { Colorizer.colorize("\u2B18", bikeInfo.getForegroundColor(), Arena.arenaBackground,
				TerminalColorMode.BRIGHT) };
		spriteLeft = new String[] { Colorizer.colorize("\u2B16", bikeInfo.getForegroundColor(), Arena.arenaBackground,
				TerminalColorMode.BRIGHT) };
		spriteRight = new String[] { Colorizer.colorize("\u2B17", bikeInfo.getForegroundColor(), Arena.arenaBackground,
				TerminalColorMode.BRIGHT) };
	}

	/**
	 * Revives and spawns this bike in the world at spawnLocation.
	 * 
	 * @param spawnLocation
	 *            The location and direction at which to spawn this bike
	 */
	public final void spawn(SpawnLocation spawnLocation) {
		this.position = new Point(spawnLocation.startPoint);
		this.currentDirection = spawnLocation.startDirection;
		this.previousDirection = currentDirection;
		this.alive = true;
		this.aliveTime = 0;

		DrawManager.registerForDraw(this);
		CollisionManager.registerForCollision(this);
	}

	protected final void turnLeft() {
		// This setup prevents 180 degree turns
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

	protected final void turnRight() {
		// This setup prevents 180 degree turns
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

	/**
	 * Called whenever this bike is updated.
	 * 
	 * @param pressedKeys
	 *            The keys reported as currently pressed
	 * @param deltaMillis
	 *            The time since this method was last called
	 */
	protected abstract void onUpdate(Set<Keys> pressedKeys, long deltaMillis);

	/**
	 * Updates this bike, allowing it to move and extend its trail.
	 * 
	 * @param pressedKeys
	 *            The currently pressed keys
	 * @param deltaMillis
	 *            The time since this method was last called
	 */
	public void update(Set<Keys> pressedKeys, long deltaMillis) {
		this.onUpdate(pressedKeys, deltaMillis);

		updateTime += deltaMillis;

		if (alive)
			aliveTime += deltaMillis;

		speedModifier = (INITIAL_SPEED_MODIFIER
				// Handles over time acceleration
				+ Math.min(1f, aliveTime / TIME_TO_FULL_SPEED) * (1 - INITIAL_SPEED_MODIFIER))
				// Handles the initial orientation slowdown.
				* (Math.min(.65f, .65f * (aliveTime / SLOW_START_TIMEOUT)) + .35f);

		if (updateTime >= UPDATE_INTERVAL / speedModifier) {
			updateTime %= (UPDATE_INTERVAL / speedModifier);

			if (alive) {

				previousDirection = currentDirection;

				trail.spawnTrailAt(new Point(this.position));

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
			} else {
				// This bike is dead.

				/*
				 * For as long as the game is still going on, dead bikes will
				 * reduce their existing trail by 1 each update until no trail
				 * is left
				 */
				trail.shrinkTrail(1);
				if (trail.length() == 0)
					this.unSpawn();
			}
		}
	}

	public final int getControllingPlayerIndex() {
		return this.playerIndex;
	}

	public final boolean isAlive() {
		return this.alive;
	}

	/**
	 * Returns the total time in milliseconds this bike has been alive for this
	 * round.
	 */
	public final long getAliveTime() {
		return this.aliveTime;
	}

	/**
	 * Instantly removes this bike and it's associated trail.
	 */
	public void unSpawn() {
		this.trail.removeTrail();
		CollisionManager.unRegisterForCollision(this);
		DrawManager.unRegisterForDraw(this);
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
	public final Rectangle getCollisionRect() {
		return new Rectangle(this.position, 1, 1);
	}

	@Override
	public final CollideableType getCollideableType() {
		return CollideableType.BIKE;
	}

	@Override
	public final void onCollision(CollideableType collidedWithType) {
		this.alive = false;
		CollisionManager.unRegisterForCollision(this);
		DrawManager.unRegisterForDraw(this);

		// Publish score
		ScoreManager.score(getControllingPlayerIndex(), getAliveTime());
	}
}
