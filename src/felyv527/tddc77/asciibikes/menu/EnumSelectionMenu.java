package felyv527.tddc77.asciibikes.menu;

import java.util.HashSet;
import java.util.Set;

import felyv527.tddc77.asciibikes.io.Keys;

/**
 * A menu specialized for the manual selection of an enum.
 *
 * @param <E>
 *            The enum type this menu is to provide options from
 */
public class EnumSelectionMenu<E extends Enum<E>> extends ButtonedMenu {

	private final Class<E> enm;

	/**
	 * Initializes a menu specialized for the manual selection of an enum.
	 * 
	 * @param title
	 *            The title of this menu
	 * @param hint
	 *            A hint to show the user
	 * @param enm
	 *            The enum class from which an enum is to be chosen
	 */
	public EnumSelectionMenu(String title, String hint, Class<E> enm) {
		this(title, hint, enm, new HashSet<E>());
	}

	/**
	 * Initializes a menu specialized for the manual selection of an enum.
	 * 
	 * @param title
	 *            The title of this menu
	 * @param hint
	 *            A hint to show the user
	 * @param enm
	 *            The enum class from which an enum is to be chosen
	 * @param exclusionList
	 *            A set of enums to exclude from the selection
	 */
	public EnumSelectionMenu(String title, String hint, Class<E> enm, Set<E> exclusionList) {
		super(title, hint);

		this.enm = enm;

		for (E e : enm.getEnumConstants()) {
			if (!exclusionList.contains(e))
				addButton(new NavigationMenuItem(e.name(), MenuResult.GO_BACK));
		}
	}

	/**
	 * Gets the user selected enum.
	 * 
	 * <p>
	 * Should only be called after the menu has been shown.
	 */
	public E getSelection() {
		for (E e : enm.getEnumConstants()) {
			MenuItem selection = this.getSelectedMenuItem();
			if (selection != null && selection.getTitle().contains(e.name()))
				return e;
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	protected MenuResult onMenuUpdate(Set<Keys> pressedKeys) {
		return MenuResult.COMPLETED;
	}

	@Override
	protected void onPostShow() {

	}

}
