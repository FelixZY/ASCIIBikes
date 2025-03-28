package felyv527.tddc77.asciibikes;

abstract class Game {

	private static final int TARGET_UPDATE_RATE = 40, THREAD_SLEEP_OFFSET = 3;
	private static final int TARGET_LOOP_SPEED_MILLIS = 1000 / TARGET_UPDATE_RATE;

	private boolean shouldExit = false;
	private boolean isRunning = false;
	private boolean drewLastUpdate = false;

	private long updateStartTimeMillis;

	protected abstract void prepare();

	protected abstract void Update(long deltaMillis);

	protected abstract void Draw();

	protected boolean isRunningSlow() {
		return getCurrentMillis() - updateStartTimeMillis > TARGET_LOOP_SPEED_MILLIS;
	}

	private long getCurrentMillis() {
		return System.nanoTime() / 1000000;
	}

	public void Run() {
		if (isRunning)
			throw new RuntimeException("Game is already running");

		isRunning = true;
		shouldExit = false;
		this.prepare();
		updateStartTimeMillis = getCurrentMillis();
		gameLoop();
	}

	private void gameLoop() {
		while (!shouldExit) {
			Tick();
			try {
				Thread.sleep(Math.max(
						TARGET_LOOP_SPEED_MILLIS + updateStartTimeMillis - getCurrentMillis() - THREAD_SLEEP_OFFSET,
						0));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		isRunning = false;
	}

	private void Tick() {
		long lastUpdateTimeMillis = updateStartTimeMillis;
		updateStartTimeMillis = getCurrentMillis();
		this.Update(getCurrentMillis() - lastUpdateTimeMillis);

		/*
		 * Since the console is slow at drawing, we reduce the number of draw
		 * calls to 50% of the update frequency. The difference is unnoticable
		 * as for lag but reduces draw flicker substantially.
		 */
		if (!drewLastUpdate)
			this.Draw();
		drewLastUpdate = !drewLastUpdate;
	}

	protected void Exit() {
		shouldExit = true;
	}
}
