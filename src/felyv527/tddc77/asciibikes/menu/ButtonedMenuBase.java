package felyv527.tddc77.asciibikes.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import felyv527.tddc77.asciibikes.DrawHandler;
import felyv527.tddc77.asciibikes.common.Point;
import felyv527.tddc77.asciibikes.io.InputManager;
import felyv527.tddc77.asciibikes.io.Keys;

public abstract class ButtonedMenuBase extends FramedMenuBase {

	private int buttonsPerColumn = 1;

	private List<MenuItem> subMenus = new ArrayList<>();
	private List<MenuButton> menuButtons = new ArrayList<>();

	public ButtonedMenuBase(String title) {
		super(title);
	}

	public ButtonedMenuBase(String title, String message) {
		super(title, message);
	}

	protected void addButton(MenuItem item) {
		subMenus.add(item);
	}

	protected void removeButton(MenuItem item) {
		subMenus.remove(item);
	}

	@Override
	protected void onPreShow() {
		if (subMenus.isEmpty())
			addButton(new GoBackMenuItem());
	}

	protected abstract MenuResult onMenuUpdate(Set<Keys> pressedKeys);

	@Override
	protected MenuResult onShow() {
		MenuResult result = MenuResult.RESULT_OK;
		int activeIndex = 0;
		Set<Keys> pressedKeys;

		do {
			menuButtons.get(activeIndex).setActive(true);
			DrawHandler.drawFrame();
			menuButtons.get(activeIndex).setActive(false);

			pressedKeys = InputManager.readKeysBlocking();

			if (pressedKeys.contains(Keys.ARROW_UP)) {
				activeIndex--;
				if (activeIndex < 0) {
					activeIndex = menuButtons.size() - 1;
				}
			}

			if (pressedKeys.contains(Keys.ARROW_DOWN)) {
				activeIndex = (activeIndex + 1) % menuButtons.size();
			}

			if (pressedKeys.contains(Keys.ARROW_LEFT)
					&& menuButtons.size() > buttonsPerColumn) {
				int column = activeIndex / buttonsPerColumn; // Integerdivison
				int numCols = menuButtons.size() / buttonsPerColumn;

				if (column == 0) {
					activeIndex = Math.min(menuButtons.size() - 1,
							buttonsPerColumn * numCols + activeIndex);
				} else {
					activeIndex -= buttonsPerColumn;
				}
			}

			if (pressedKeys.contains(Keys.ARROW_RIGHT)
					&& menuButtons.size() > buttonsPerColumn) {
				int column = activeIndex / buttonsPerColumn; // Integerdivison
				int numCols = menuButtons.size() / buttonsPerColumn;

				if (column == numCols - 1) {
					activeIndex = Math.min(menuButtons.size() - 1,
							buttonsPerColumn * numCols + activeIndex
									% buttonsPerColumn);
				} else if (column == numCols) {
					activeIndex %= buttonsPerColumn;
				} else {
					activeIndex += buttonsPerColumn;
				}
			}

			if (pressedKeys.contains(Keys.ENTER)) {
				if (subMenus.get(activeIndex).isEnabled())
					result = subMenus.get(activeIndex).show();
			} else {
				pressedKeys.remove(Keys.ARROW_UP);
				pressedKeys.remove(Keys.ARROW_DOWN);
				pressedKeys.remove(Keys.ARROW_LEFT);
				pressedKeys.remove(Keys.ARROW_RIGHT);
				result = onMenuUpdate(pressedKeys);
			}

		} while (result == MenuResult.RESULT_OK);

		if (result == MenuResult.RESULT_GO_BACK)
			return MenuResult.RESULT_OK;
		else
			return MenuResult.RESULT_CLOSE_MENU_TREE;
	}

	@Override
	protected void registerForDraw() {
		super.registerForDraw();
		createButtons();

	}

	@Override
	protected void unRegisterForDraw() {
		removeButtons();
		super.unRegisterForDraw();
	}

	private void createButtons() {
		int starty = Math.min(
				MESSAGE_START.getY()
						+ messageSprite.getStringRepresentation().length + 1,
				MENU_CONTENT_FRAME.getBottom());
		buttonsPerColumn = Math.max(1, (MENU_FRAME.getHeight() - starty) / 2); // MENU_FRAME
																				// since
																				// ButtonYStart
																				// is
																				// based
																				// on
																				// the
																				// window,
																				// not
																				// content
		int columns = (int) Math.ceil(subMenus.size()
				/ (float) buttonsPerColumn);
		int columnOffset = MENU_CONTENT_FRAME.getWidth() / (columns * 2);
		int startX = MENU_CONTENT_FRAME.getX() + columnOffset;

		// Remove any pre-existing buttons to avoid duplicates
		removeButtons();

		for (int column = 0; column < columns; column++) {
			int x = startX + column * columnOffset * 2;

			for (int buttonIndex = 0; buttonIndex < buttonsPerColumn; buttonIndex++) {
				int y = starty + buttonIndex * 2;
				int index = buttonIndex + column * buttonsPerColumn;
				if (index < subMenus.size()) {
					MenuButton button = new MenuButton(subMenus.get(index),
							new Point(x, y));

					DrawHandler.registerForFrontDraw(button); // Knappar måste
																// alltid vara
																// synliga,
																// därför ritas
																// de till Front
					menuButtons.add(button);
				}
			}
		}
	}

	private void removeButtons() {
		for (MenuButton button : menuButtons) {
			DrawHandler.unRegisterForFrontDraw(button);
		}
		menuButtons.clear();
	}

}
