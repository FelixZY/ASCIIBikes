package felyv527.tddc77.asciibikes.menu;

import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalColorMode;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.Point;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.drawing.AsciiSprite;
import felyv527.tddc77.asciibikes.drawing.Drawable;

/**
 * A sprite used to represent {@link MenuItem}s as buttons in a
 * {@link ButtonedMenu}
 *
 */
final class MenuButtonSprite implements Drawable {

	private boolean active = false;
	private MenuItem item;
	private Point location;

	/**
	 * Creates a new {@link MenuButtonSprite} at the specified location
	 * 
	 * @param item
	 *            The MenuItem associated with this {@link MenuButtonSprite}
	 * @param location
	 *            The location of this button on the screen
	 */
	public MenuButtonSprite(MenuItem item, Point location) {
		this.item = item;
		this.location = location;
	}

	/**
	 * Sets whether or not this button is currently highlighted
	 * 
	 * @param active
	 *            {@code true} if this button is currently highlighted. Else
	 *            {@code false}
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	private String getTitle() {
		if (this.active && item.isEnabled()) { // Active and enabled
			return " > " + item.getTitle() + " < ";

		} else if (!this.active) { // Inactive
			return " " + item.getTitle() + " ";

		} else { // Active, but not enabled
			return " X " + item.getTitle() + " X ";
		}
	}

	public Point getSpriteOrigin() {
		return new Point(location.getX() - getTitle().length() / 2, location.getY());
	}

	@Override
	public AsciiSprite getAsciiSprite() {
		TerminalForegroundColor foregroundColor;
		TerminalBackgroundColor backgroundColor;
		TerminalColorMode colorMode;

		if (this.active && item.isEnabled()) { // Active and enabled
			foregroundColor = TerminalForegroundColor.WHITE;
			backgroundColor = TerminalBackgroundColor.BLACK;
			colorMode = TerminalColorMode.BRIGHT;

		} else if (!this.active) { // Inactive
			foregroundColor = TerminalForegroundColor.BLACK;
			backgroundColor = TerminalBackgroundColor.WHITE;
			colorMode = TerminalColorMode.NORMAL;

		} else { // Active, but not enabled
			foregroundColor = TerminalForegroundColor.RED;
			backgroundColor = TerminalBackgroundColor.BLACK;
			colorMode = TerminalColorMode.BRIGHT;

		}

		return new AsciiSprite(getSpriteOrigin(),
				new String[] { Colorizer.colorize(getTitle(), foregroundColor, backgroundColor, colorMode) });
	}

}
