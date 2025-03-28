package felyv527.tddc77.asciibikes.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import felyv527.tddc77.asciibikes.Point;
import felyv527.tddc77.asciibikes.drawing.DrawManager;
import felyv527.tddc77.asciibikes.io.InputManager;
import felyv527.tddc77.asciibikes.io.Keys;

/**
 * A framed menu with a title, description and buttons
 *
 * <p>
 * This menu will be shown until told to {@link MenuResult#GO_BACK} or
 * {@link MenuResult#CLOSE}
 */
public abstract class ButtonedMenu extends Menu {

	private int buttonsPerColumn = 1;

	/**
	 * The currently highlighted button index
	 */
	private int activeIndex = -1;

	private List<MenuItem> subMenus = new ArrayList<>();
	private List<MenuButtonSprite> menuButtons = new ArrayList<>();

	/**
	 * Initializes a menu with the specified title
	 * 
	 * @param title
	 *            The title of this menu
	 */
	public ButtonedMenu(String title) {
		super(title);
	}

	/**
	 * Initializes a menu with the specified title and description
	 * 
	 * @param title
	 *            The title of this menu
	 * @param description
	 *            The description of this menu
	 */
	public ButtonedMenu(String title, String description) {
		super(title, description);
	}

	/**
	 * Returns the last selected MenuItem or {@code null} if this menu has not
	 * been shown.
	 */
	protected MenuItem getSelectedMenuItem() {
		if (activeIndex < 0 || subMenus.size() <= activeIndex)
			return null;
		else
			return subMenus.get(activeIndex);
	}

	/**
	 * Adds a button for navigating to the specified {@link MenuItem}
	 * 
	 * @param item
	 *            The {@link MenuItem} for which to add a button
	 */
	protected void addButton(MenuItem item) {
		subMenus.add(item);
	}

	@Override
	protected void onPreShow() {
		// Makes sure there is at least one button in this menu.
		if (subMenus.isEmpty())
			addButton(new NavigationMenuItem("Back", MenuResult.GO_BACK));
	}

	/**
	 * Called whenever this menu receives input.
	 * 
	 * @param pressedKeys
	 *            The currently pressed keys
	 * @return One of the {@link MenuResult} values
	 */
	protected abstract MenuResult onMenuUpdate(Set<Keys> pressedKeys);

	@Override
	protected MenuResult onShow() {
		MenuResult result = MenuResult.COMPLETED;
		Set<Keys> pressedKeys;

		activeIndex = 0;

		do {
			menuButtons.get(activeIndex).setActive(true);
			DrawManager.drawFrame();
			menuButtons.get(activeIndex).setActive(false);

			// Wait for input
			pressedKeys = InputManager.readKeysBlocking();

			// Handle navigation input

			if (pressedKeys.contains(Keys.ARROW_UP)) {
				activeIndex--;
				if (activeIndex < 0) {
					activeIndex = menuButtons.size() - 1;
				}
			}

			if (pressedKeys.contains(Keys.ARROW_DOWN)) {
				activeIndex = (activeIndex + 1) % menuButtons.size();
			}

			if (pressedKeys.contains(Keys.ARROW_LEFT) && menuButtons.size() > buttonsPerColumn) {
				int column = activeIndex / buttonsPerColumn; // Integer division
				int numCols = menuButtons.size() / buttonsPerColumn;

				if (column == 0) {
					activeIndex = Math.min(menuButtons.size() - 1, buttonsPerColumn * numCols + activeIndex);
				} else {
					activeIndex -= buttonsPerColumn;
				}
			}

			if (pressedKeys.contains(Keys.ARROW_RIGHT) && menuButtons.size() > buttonsPerColumn) {
				int column = activeIndex / buttonsPerColumn; // Integer division
				int numCols = menuButtons.size() / buttonsPerColumn;

				if (column == numCols - 1) {
					activeIndex = Math.min(menuButtons.size() - 1,
							buttonsPerColumn * numCols + activeIndex % buttonsPerColumn);
				} else if (column == numCols) {
					activeIndex %= buttonsPerColumn;
				} else {
					activeIndex += buttonsPerColumn;
				}
			}

			// Finally we check if the user pressed {@link Keys#ENTER}

			if (pressedKeys.contains(Keys.ENTER)) {
				/*
				 * If the {@link MenuItem} at the selected index can be shown,
				 * it is shown and its {@link MenuResult} dictates how this menu
				 * should continue.
				 */
				if (subMenus.get(activeIndex).isEnabled())
					result = subMenus.get(activeIndex).show();
			} else {
				/*
				 * If the user did not press {@link Keys#ENTER} we'll let {@link
				 * #onMenuUpdate(pressedKeys)} dictate how this menu should
				 * continue.
				 * 
				 * The arrow keys are not included in the pressedKeys sent to
				 * this method though, as these are strictly to be used for
				 * button navigation purposes in a {@link ButtonedMenu}.
				 */
				pressedKeys.remove(Keys.ARROW_UP);
				pressedKeys.remove(Keys.ARROW_DOWN);
				pressedKeys.remove(Keys.ARROW_LEFT);
				pressedKeys.remove(Keys.ARROW_RIGHT);
				result = onMenuUpdate(pressedKeys);
			}

			// While the result is "completed" we keep showing this menu.
		} while (result == MenuResult.COMPLETED);

		if (result == MenuResult.GO_BACK)
			return MenuResult.COMPLETED;
		else
			return MenuResult.EXIT;
	}

	@Override
	protected void registerForDraw() {
		super.registerForDraw();
		createButtons();

	}

	@Override
	protected void unRegisterForDraw() {
		menuButtons.clear();
		super.unRegisterForDraw();
	}

	/**
	 * Creates and registers the actual button sprites
	 */
	private void createButtons() {
		int startY = Math.min(DESCRIPTION_START.getY() + this.getDescriptionHeight() + 1,
				MENU_CONTENT_FRAME.getBottom());

		buttonsPerColumn = Math.max(1, (MENU_FRAME.getHeight() - startY) / 2);

		int columns = (int) Math.ceil(subMenus.size() / (float) buttonsPerColumn);

		int columnOffset = MENU_CONTENT_FRAME.getWidth() / (columns * 2);

		int startX = MENU_CONTENT_FRAME.getX() + columnOffset;

		for (int column = 0; column < columns; column++) {
			int x = startX + column * columnOffset * 2;

			for (int buttonIndex = 0; buttonIndex < buttonsPerColumn; buttonIndex++) {
				int y = startY + buttonIndex * 2;
				int index = buttonIndex + column * buttonsPerColumn;
				if (index < subMenus.size()) {
					MenuButtonSprite button = new MenuButtonSprite(subMenus.get(index), new Point(x, y));

					/*
					 * Buttons are considered extremely important which is why
					 * they are drawn to the front layer.
					 */
					DrawManager.registerForFrontDraw(button);

					menuButtons.add(button);
				}
			}
		}
	}

}
