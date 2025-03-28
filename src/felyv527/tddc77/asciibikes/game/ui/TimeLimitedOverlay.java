package felyv527.tddc77.asciibikes.game.ui;

import felyv527.tddc77.asciibikes.drawing.DrawManager;
import felyv527.tddc77.asciibikes.drawing.Drawable;
import felyv527.tddc77.asciibikes.game.Game;

/**
 * A time limited game overlay that will block until completed.
 *
 */
public abstract class TimeLimitedOverlay implements Drawable {

	private int activeMillis;
	private long activeTime;

	private boolean isShowing = false;

	private Game driver;

	public TimeLimitedOverlay() {

		final TimeLimitedOverlay self = this;

		driver = new Game(10) {

			@Override
			protected void prepare() {
				DrawManager.registerForFrontDraw(self);
				activeTime = 0;
				isShowing = true;
			}

			@Override
			protected void update(long deltaMillis) {
				activeTime += deltaMillis;
				if (activeTime >= self.activeMillis) {
					this.exit();
				}
			}

			@Override
			protected void onUnload() {
				isShowing = false;
				DrawManager.unRegisterForFrontDraw(self);
			}

			@Override
			protected void draw() {
				DrawManager.drawFrame();
			}
		};

	}

	/**
	 * Freezes any active window and displays this overlay for the specified
	 * amount of time.
	 * 
	 * NOTE: A DrawHandler session is required to show this overlay.
	 * 
	 * @param showForMillis
	 *            The number of milliseconds to show this overlay for.
	 */
	public final void show(int showForMillis) {
		this.activeMillis = showForMillis;
		driver.run();
	}

	/**
	 * Returns the remaining milliseconds until this overlay is hidden
	 */
	protected final long getRemainingMillis() {
		if (!isShowing)
			return 0;
		else
			return Math.max(0, activeMillis - activeTime);
	}

}
