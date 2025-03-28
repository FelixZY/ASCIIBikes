package felyv527.tddc77.asciibikes.menu;

import java.util.Set;

import felyv527.tddc77.asciibikes.io.InputManager;
import felyv527.tddc77.asciibikes.io.Keys;

/**
 * A menu specialized for reading a single button press
 *
 */
public class KeyReaderMenu extends Menu {

	private Keys pressedKey = Keys.NONE;

	/**
	 * Initializes a menu specialized for reading a single button press.
	 * 
	 * @param title
	 *            The title of this menu
	 * @param hint
	 *            A hint to show the user
	 */
	public KeyReaderMenu(String title, String hint) {
		super(title, hint);
	}

	/**
	 * Returns the read key.
	 */
	public Keys getKey() {
		return this.pressedKey;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	protected void onPreShow() {
		pressedKey = Keys.NONE;
	}

	@Override
	protected MenuResult onShow() {
		Set<Keys> pressedSet;
		do {
			pressedSet = InputManager.readKeysBlocking();
			// We want to read 1 and only 1 key.
		} while (pressedSet.size() != 1);

		pressedKey = pressedSet.iterator().next();

		return MenuResult.COMPLETED;
	}

	@Override
	protected void onPostShow() {

	}

}
