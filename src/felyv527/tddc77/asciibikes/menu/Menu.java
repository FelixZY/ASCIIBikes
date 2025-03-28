package felyv527.tddc77.asciibikes.menu;

import felyv527.tddc77.asciibikes.Point;
import felyv527.tddc77.asciibikes.Rectangle;
import felyv527.tddc77.asciibikes.StringTools;
import felyv527.tddc77.asciibikes.TerminalDimensions;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalColorMode;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.drawing.AsciiSprite;
import felyv527.tddc77.asciibikes.drawing.BackgroundSprite;
import felyv527.tddc77.asciibikes.drawing.DrawManager;
import felyv527.tddc77.asciibikes.font.HeadlineFont;

/**
 * A framed menu with a title and description
 *
 */
public abstract class Menu implements MenuItem {

	/**
	 * The outer rectangle of a {@link Menu} frame
	 */
	protected static final Rectangle MENU_FRAME = new Rectangle(2, 1, TerminalDimensions.getNumColumns() - 4,
			TerminalDimensions.getNumLines() - 2);

	/**
	 * The inner rectangle of a {@link Menu} frame (top left corner)
	 */
	protected static final Rectangle MENU_CONTENT_FRAME = new Rectangle(MENU_FRAME.getLeft() + 1,
			MENU_FRAME.getTop() + 1, MENU_FRAME.getWidth() - 2, MENU_FRAME.getHeight() - 2);

	/**
	 * The origin coordinates of the title
	 */
	protected static final Point TITLE_START = new Point(MENU_CONTENT_FRAME.getLeft(), MENU_CONTENT_FRAME.getTop());

	/**
	 * The width of the displayed description
	 */
	protected static final int DESCRIPTION_WIDTH = MENU_CONTENT_FRAME.getWidth() - 4;

	/**
	 * The origin coordinates of the description (top left corner)
	 */
	protected static final Point DESCRIPTION_START = new Point(
			MENU_CONTENT_FRAME.getLeft() + (MENU_CONTENT_FRAME.getWidth() - DESCRIPTION_WIDTH) / 2,
			TITLE_START.getY() + (new HeadlineFont()).applyFont("A").length + 1);

	private static final AsciiSprite backgroundSprite = new BackgroundSprite(TerminalBackgroundColor.BLACK),
			windowSprite;

	private AsciiSprite titleSprite, descriptionSprite;

	private boolean isShowing = false;

	private String title;

	static {
		// This is the white window frame behind every menu
		windowSprite = new AsciiSprite(new Point(MENU_FRAME.getX(), MENU_FRAME.getY()),
				Colorizer.colorize(StringTools.frame(MENU_FRAME), TerminalForegroundColor.BLACK,
						TerminalBackgroundColor.WHITE, TerminalColorMode.BRIGHT));
	}

	/**
	 * Initializes a menu with the specified title
	 * 
	 * @param title
	 *            The title of this menu
	 */
	public Menu(String title) {
		this(title, "");
	}

	/**
	 * Initializes a menu with the specified title and description
	 * 
	 * @param title
	 *            The title of this menu
	 * @param description
	 *            The description of this menu
	 */
	public Menu(String title, String description) {
		setTitle(title);
		setDescription(description);
	}

	/**
	 * Called right before this menu is shown.
	 * 
	 * <p>
	 * No DrawHandler session has been acquired at this point in the menu life
	 * cycle.
	 */
	protected abstract void onPreShow();

	/**
	 * Called when the menu is to be shown.
	 * 
	 * <p>
	 * A DrawHandler session has been acquired at this point in the menu life
	 * cycle.
	 * 
	 * @return One of the {@link MenuResult} values
	 */
	protected abstract MenuResult onShow();

	/**
	 * Called when the menu is to be closed.
	 * 
	 * <p>
	 * The DrawHandler session has been ended at this point in the menu life
	 * cycle.
	 */
	protected abstract void onPostShow();

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public MenuResult show() {
		onPreShow();
		registerForDraw();
		DrawManager.drawFrame();
		MenuResult result = onShow();
		unRegisterForDraw();
		onPostShow();
		return result;
	}

	protected void setTitle(String title) {
		this.title = title;

		String[] titleRepresentation;

		if (title.length() > 0) {

			titleRepresentation = Colorizer.colorize(
					StringTools.center((new HeadlineFont()).applyFont(title), MENU_CONTENT_FRAME.getWidth()),
					TerminalForegroundColor.WHITE, TerminalBackgroundColor.BLACK, TerminalColorMode.BRIGHT);

		} else {
			titleRepresentation = new String[0];
		}

		/*
		 * If we are currently showing this menu, we need to unregister the
		 * titleSprite before applying any changes.
		 */
		if (isShowing)
			DrawManager.unRegisterForOverlayDraw(titleSprite);

		titleSprite = new AsciiSprite(TITLE_START, titleRepresentation);

		/*
		 * Likewise, if the menu is showing, we will want to show the
		 * titleSprite again.
		 */
		if (isShowing)
			DrawManager.registerForOverlayDraw(titleSprite);
	}

	protected void setDescription(String description) {
		description = StringTools.wrap(description, DESCRIPTION_WIDTH);

		String[] messageRepresentation;

		if (description.length() > 0) {
			messageRepresentation = Colorizer.colorize(description.split("\n"), TerminalForegroundColor.BLACK,
					TerminalBackgroundColor.WHITE, TerminalColorMode.NORMAL);
		} else {
			messageRepresentation = new String[0];
		}

		/*
		 * If we are currently showing this menu, we need to unregister the
		 * descriptionSprite before applying any changes.
		 */
		if (isShowing)
			DrawManager.unRegisterForOverlayDraw(descriptionSprite);

		descriptionSprite = new AsciiSprite(DESCRIPTION_START, messageRepresentation);

		/*
		 * Likewise, if the menu is showing, we will want to show the
		 * descriptionSprite again.
		 */
		if (isShowing)
			DrawManager.registerForOverlayDraw(descriptionSprite);
	}

	protected int getTitleHeight() {
		return titleSprite.getStringRepresentation().length;
	}

	protected int getDescriptionHeight() {
		return descriptionSprite.getStringRepresentation().length;
	}

	/**
	 * Called when this menu performs its draw registration. Any code below
	 * {@code super()} will have a {@link DrawManager} session acquired.
	 */
	protected void registerForDraw() {
		DrawManager.startSession();
		DrawManager.registerForDraw(backgroundSprite);
		DrawManager.registerForDraw(windowSprite);
		DrawManager.registerForOverlayDraw(titleSprite);
		DrawManager.registerForOverlayDraw(descriptionSprite);

		isShowing = true;
	}

	/**
	 * Called when this menu performs its draw registration. Any code below
	 * {@code super()} will not have any associated {@link DrawManager} session
	 * available.
	 */
	protected void unRegisterForDraw() {
		DrawManager.endSession();

		isShowing = false;
	}
}
