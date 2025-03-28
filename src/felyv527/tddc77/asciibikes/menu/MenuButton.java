package felyv527.tddc77.asciibikes.menu;

import felyv527.tddc77.asciibikes.Drawable;
import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalColorMode;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.common.AsciiSprite;
import felyv527.tddc77.asciibikes.common.Point;

class MenuButton implements Drawable {

	private boolean active = false;
	private MenuItem item;
	private Point location;

	MenuButton(MenuItem item, Point location) {
		this.item = item;
		this.location = location;
	}

	void setActive(boolean active) {
		this.active = active;
	}

	void setLocation(Point location) {
		this.location = location;
	}

	private String getTitle() {
		if (this.active && item.isEnabled())
			return " > " + item.getTitle() + " < ";
		else if (!this.active)
			return " " + item.getTitle() + " ";
		else
			return " X " + item.getTitle() + " X ";
	}

	public Point getSpriteOrigin() {
		return new Point(location.getX() - getTitle().length() / 2,
				location.getY());
	}

	@Override
	public AsciiSprite getAsciiSprite() {
		if (this.active && item.isEnabled()) {
			// Active and enabled
			return new AsciiSprite(getSpriteOrigin(),
					new String[] { Colorizer.colorize(getTitle(),
							TerminalForegroundColor.YELLOW,
							TerminalBackgroundColor.BLACK,
							TerminalColorMode.BRIGHT) });
		} else if (!this.active) {
			// Inactive
			return new AsciiSprite(getSpriteOrigin(),
					new String[] { Colorizer.colorize(getTitle(),
							TerminalForegroundColor.BLACK,
							TerminalBackgroundColor.WHITE,
							TerminalColorMode.NORMAL) });
		} else {
			// Active, but not enabled
			return new AsciiSprite(getSpriteOrigin(),
					new String[] { Colorizer.colorize(getTitle(),
							TerminalForegroundColor.RED,
							TerminalBackgroundColor.BLACK,
							TerminalColorMode.BRIGHT) });
		}
	}

}
