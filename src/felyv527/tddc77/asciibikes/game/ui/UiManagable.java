package felyv527.tddc77.asciibikes.game.ui;

import felyv527.tddc77.asciibikes.drawing.Drawable;

/**
 * An object that can be managed by {@link UiManager}
 *
 */
interface UiManagable extends Drawable {

	/**
	 * Called when a new round is started in the game
	 */
	void onRoundStart();

	/**
	 * Called on every update in the game loop
	 * 
	 * @param deltaMillis
	 *            The number of milliseconds since the last update
	 */
	void update(long deltaMillis);

	/**
	 * Called when a round is finished in the game.
	 */
	void onRoundEnd();

}
