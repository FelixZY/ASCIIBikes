package felyv527.tddc77.asciibikes.game;

/**
 * Provides a basic update and draw loop suitable for game logic.
 * 
 * <p>
 * Loosely based off of the Game class found in XNA
 */
public abstract class Game {

	private final int targetTickIntervall;

	private boolean shouldExit = false;
	private boolean isRunning = false;

	private long updateStartTimeMillis;

	/**
	 * Constructs and initializes this class which provides a basic update and
	 * draw loop suitable for game logic.
	 * 
	 * @param targetTicksPerSecond
	 *            The target number of ticks (updates) per second for this
	 *            {@link Game}.
	 */
	protected Game(int targetTicksPerSecond) {
		targetTickIntervall = 1000 / targetTicksPerSecond;
	}

	/**
	 * Called when this game is started, right before the first update in the
	 * game loop.
	 */
	protected abstract void prepare();

	/**
	 * Called once every tick to update any necessary game logic.
	 * 
	 * @param deltaMillis
	 *            The time elapsed since the last call to this method.
	 */
	protected abstract void update(long deltaMillis);

	/**
	 * Called once every tick to perform any necessary drawing logic.
	 */
	protected abstract void draw();

	/**
	 * Called right before this game exits, allowing for finalization logic.
	 */
	protected abstract void onUnload();

	/**
	 * Starts a new game loop and blocks until completed.
	 */
	public void run() {
		if (isRunning)
			throw new RuntimeException("Game is already running");

		isRunning = true;
		shouldExit = false;
		this.prepare();
		updateStartTimeMillis = System.currentTimeMillis();
		gameLoop();
	}

	/**
	 * Breaks the game loop before the next tick and finishes the {@link Game}
	 * gracefully.
	 */
	protected final void exit() {
		shouldExit = true;
	}

	private void gameLoop() {
		while (!shouldExit) {
			tick();
			try {
				/*
				 * We only want to update the game at fixed intervals. When
				 * nothing is actually being done we let the thread sleep until
				 * the next tick.
				 * 
				 * -1 added as to not oversleep.
				 */
				Thread.sleep(Math.max(targetTickIntervall + updateStartTimeMillis - System.currentTimeMillis() - 1, 0));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		onUnload();
		isRunning = false;
	}

	private void tick() {
		long lastUpdateTimeMillis = updateStartTimeMillis;
		updateStartTimeMillis = System.currentTimeMillis();
		this.update(updateStartTimeMillis - lastUpdateTimeMillis);
		if (!shouldExit)
			this.draw();
	}
}
