package felyv527.tddc77.asciibikes.menu;

import java.util.Set;

import felyv527.tddc77.asciibikes.io.Keys;

/**
 * A basic menu with a title, description and buttons.
 *
 */
public class SimpleMenu extends ButtonedMenu {

	/**
	 * Initializes a menu with the specified title, description and buttons for
	 * all supplied {@link MenuItem}s
	 * 
	 * @param title
	 *            The title of this menu
	 * @param description
	 *            The description to show for this menu
	 * @param subMenus
	 *            The submenu tree for this menu
	 */
	public SimpleMenu(String title, String description, MenuItem... subMenus) {
		super(title, description);
		for (MenuItem menuItem : subMenus) {
			addButton(menuItem);
		}
	}

	@Override
	protected MenuResult onMenuUpdate(Set<Keys> pressedKeys) {
		return MenuResult.COMPLETED;
	}

	@Override
	protected void onPostShow() {
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
