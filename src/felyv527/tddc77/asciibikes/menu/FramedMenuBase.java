package felyv527.tddc77.asciibikes.menu;

import felyv527.tddc77.asciibikes.DrawHandler;
import felyv527.tddc77.asciibikes.Drawable;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalColorMode;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.common.AsciiSprite;
import felyv527.tddc77.asciibikes.common.BackgroundSprite;
import felyv527.tddc77.asciibikes.common.Point;
import felyv527.tddc77.asciibikes.common.Rectangle;
import felyv527.tddc77.asciibikes.common.StringTools;
import felyv527.tddc77.asciibikes.io.TerminalInfo;
import felyv527.tddc77.asciibikes.text.HeadlineFont;
import felyv527.tddc77.asciibikes.text.StandardFont;
import felyv527.tddc77.asciibikes.text.TextAlignment;

public abstract class FramedMenuBase implements MenuItem, Drawable {

	protected static final Rectangle MENU_FRAME = new Rectangle(2, 1,
			TerminalInfo.getTerminalCols() - 4,
			TerminalInfo.getTerminalLines() - 2);

	protected static final Rectangle MENU_CONTENT_FRAME = new Rectangle(
			MENU_FRAME.getLeft() + 1, MENU_FRAME.getTop() + 1,
			MENU_FRAME.getWidth() - 2, MENU_FRAME.getHeight() - 2);

	protected static final Point TITLE_START = new Point(
			MENU_CONTENT_FRAME.getLeft(), MENU_CONTENT_FRAME.getTop());

	protected static final Point MESSAGE_START = new Point(
			MENU_CONTENT_FRAME.getLeft() + 2, TITLE_START.getY()
					+ (new HeadlineFont()).applyFont("A").length + 1);

	private static final AsciiSprite backgroundSprite = new BackgroundSprite(
			TerminalBackgroundColor.BLACK);

	protected static final String[] spriteBase;

	protected AsciiSprite titleSprite, messageSprite;

	protected boolean isShowing = false;

	private String title;

	static {
		spriteBase = Colorizer.colorize(StringTools.frame(MENU_FRAME, 0),
				TerminalForegroundColor.BLACK, TerminalBackgroundColor.WHITE,
				TerminalColorMode.BRIGHT);
	}

	public FramedMenuBase(String title) {
		this(title, "");
	}

	public FramedMenuBase(String title, String message) {
		setTitle(title);
		setMessage(message);
	}

	protected abstract void onPreShow();

	protected abstract MenuResult onShow();

	protected abstract void onPostShow();

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public AsciiSprite getAsciiSprite() {
		return new AsciiSprite(new Point(MENU_FRAME.getX(), MENU_FRAME.getY()),
				spriteBase);
	}

	@Override
	public MenuResult show() {
		onPreShow();
		registerForDraw();
		DrawHandler.drawFrame();
		MenuResult result = onShow();
		unRegisterForDraw();
		onPostShow();
		return result;
	}

	protected void setTitle(String title) {
		this.title = title;

		if (title.length() > 0) {
			String[] fontedTitle = (new HeadlineFont()).applyFont(title,
					TextAlignment.CENTER);
			Colorizer.colorize(
					StringTools.center(fontedTitle,
							MENU_CONTENT_FRAME.getWidth()),
					TerminalForegroundColor.YELLOW,
					TerminalBackgroundColor.BLACK, TerminalColorMode.BRIGHT);

			titleSprite = new AsciiSprite(TITLE_START, fontedTitle);
		} else {
			titleSprite = new AsciiSprite(new Point(), new String[0]);
		}
	}

	protected void setMessage(String message) {
		message = StringTools.wrap(message, MENU_CONTENT_FRAME.getWidth() - 4);

		String[] fontedMessage;

		if (message.length() > 0) {
			fontedMessage = (new StandardFont()).applyFont(message,
					TextAlignment.LEFT);

			fontedMessage = Colorizer.colorize(fontedMessage,
					TerminalForegroundColor.BLACK,
					TerminalBackgroundColor.WHITE, TerminalColorMode.NORMAL);
		} else {
			fontedMessage = new String[0];
		}

		if (isShowing) {
			this.unRegisterForDraw();
			messageSprite = new AsciiSprite(MESSAGE_START, fontedMessage);
			this.registerForDraw();
		} else {
			messageSprite = new AsciiSprite(MESSAGE_START, fontedMessage);
		}
	}

	protected void registerForDraw() {
		DrawHandler.startSession();
		DrawHandler.registerForDraw(backgroundSprite);
		DrawHandler.registerForDraw(this);
		DrawHandler.registerForOverlayDraw(titleSprite);
		DrawHandler.registerForOverlayDraw(messageSprite);

		isShowing = true;
	}

	protected void unRegisterForDraw() {
		DrawHandler.unRegisterForOverlayDraw(titleSprite);
		DrawHandler.unRegisterForOverlayDraw(messageSprite);
		DrawHandler.unRegisterForDraw(this);
		DrawHandler.unRegisterForDraw(backgroundSprite);
		DrawHandler.endSession();

		isShowing = false;
	}
}
