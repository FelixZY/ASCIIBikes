package felyv527.tddc77.asciibikes.menu;

import java.util.Set;

import felyv527.tddc77.asciibikes.io.Keys;

public class Menu extends ButtonedMenuBase {

	public Menu(String title, String message, MenuItem... subMenus) {
		super(title, message);
		for (MenuItem menuItem : subMenus) {
			addButton(menuItem);
		}
	}

	@Override
	protected MenuResult onMenuUpdate(Set<Keys> pressedKeys) {
		return MenuResult.RESULT_OK;
	}

	@Override
	protected void onPostShow() {
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
