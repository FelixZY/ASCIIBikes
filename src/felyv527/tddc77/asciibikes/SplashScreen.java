package felyv527.tddc77.asciibikes;

import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalColorMode;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.drawing.AsciiSprite;
import felyv527.tddc77.asciibikes.drawing.BackgroundSprite;
import felyv527.tddc77.asciibikes.drawing.DrawManager;
import felyv527.tddc77.asciibikes.font.HeadlineFont;
import felyv527.tddc77.asciibikes.game.Game;
import felyv527.tddc77.asciibikes.io.InputManager;

/**
 * The ASCIIBikes splash screen.
 *
 */
class SplashScreen extends Game {

	private static final int INIT_WAIT = 1000, BLINK_DELAY = 750;
	private static final String CREDIT = "Created in Dec 2016 - Jan 2017 by\nFelix Zedén Yverås (felyv527)\nIT1\n\n\nTDDC77 - Object oriented programming\nLinköping University";

	private static AsciiSprite titleSprite, creditSprite, startSprite;

	private int timer = 0;
	private boolean isBlinkVisible = false;

	/**
	 * Indicates whether the splash screen has been seen and can be skipped.
	 */
	private boolean hasWaited = false;

	/**
	 * Determines whether the splash screen will be drawn next draw call.
	 * 
	 * <p>
	 * Frequent draw calls will induce flicker in the terminal. By minimizing
	 * drawing to when it is actually required, most noticeable flicker is
	 * eliminated.
	 */
	private boolean requireDraw = true;

	static {
		createTitleSprite();
		createCreditSprite();
		createStartSprite();
	}

	public SplashScreen() {
		super(5);
	}

	@Override
	protected void prepare() {
		DrawManager.startSession();

		DrawManager.registerForDraw(new BackgroundSprite(TerminalBackgroundColor.WHITE));
		DrawManager.registerForOverlayDraw(titleSprite);
		DrawManager.registerForOverlayDraw(creditSprite);
	}

	@Override
	protected void update(long deltaMillis) {

		timer += deltaMillis;

		if (!hasWaited && timer >= INIT_WAIT) {
			// We start of by showing the splash screen for INIT_WAIT
			// milliseconds

			hasWaited = true;
			timer -= INIT_WAIT + BLINK_DELAY;

			// The user should not be able to skip the splash screen until they
			// are prompted to do so. Any key presses during the initial waiting
			// time are therefore removed here.
			InputManager.getPressedKeys();
		}

		if (hasWaited) {
			// At this stage we flash the "press any key" prompt for the user
			// and await input
			if (!InputManager.getPressedKeys().isEmpty()) {
				this.exit();
			} else {
				if (timer >= BLINK_DELAY) {
					requireDraw = true;
					isBlinkVisible = !isBlinkVisible;
					if (isBlinkVisible)
						DrawManager.registerForOverlayDraw(startSprite);
					else
						DrawManager.unRegisterForOverlayDraw(startSprite);
				}

				timer %= BLINK_DELAY;
			}
		}
	}

	@Override
	protected void onUnload() {
		DrawManager.endSession();
	}

	@Override
	protected void draw() {
		/*
		 * Frequent draw calls will induce flicker in the terminal. By
		 * minimizing drawing to when it is actually required, most noticeable
		 * flicker is eliminated.
		 */

		if (requireDraw) {
			DrawManager.drawFrame();
			requireDraw = false;
		}
	}

	private static void createTitleSprite() {
		String[] title = (new HeadlineFont()).applyFont("ASCIIBikes");

		title = StringTools.center(title, TerminalDimensions.getNumColumns());

		String[] colorizedTitle = Colorizer.colorize(title, TerminalForegroundColor.WHITE,
				TerminalBackgroundColor.BLACK, TerminalColorMode.BRIGHT);

		titleSprite = new AsciiSprite(new Point(0, 2), colorizedTitle);
	}

	private static void createCreditSprite() {
		String[] credits = CREDIT.split("\n");

		credits = StringTools.center(credits, TerminalDimensions.getNumColumns());

		// Created in Dec 2016 - Jan 2017 by
		credits[0] = Colorizer.colorize(credits[0], TerminalForegroundColor.BLACK, TerminalBackgroundColor.YELLOW);

		// Felix Zedén Yverås (felyv527)
		credits[1] = Colorizer.colorize(credits[1], TerminalForegroundColor.WHITE, TerminalBackgroundColor.MAGENTA,
				TerminalColorMode.BRIGHT);

		// IT1
		credits[2] = Colorizer.colorize(credits[2], TerminalForegroundColor.BLACK, TerminalBackgroundColor.YELLOW);

		// newlines
		credits[3] = Colorizer.colorize(credits[3], TerminalBackgroundColor.WHITE);
		credits[4] = Colorizer.colorize(credits[4], TerminalBackgroundColor.WHITE);

		// TDDC77 - Object oriented programming
		credits[5] = Colorizer.colorize(credits[5], TerminalForegroundColor.WHITE, TerminalBackgroundColor.CYAN);

		// Linköping University
		credits[6] = Colorizer.colorize(credits[6], TerminalForegroundColor.WHITE, TerminalBackgroundColor.CYAN,
				TerminalColorMode.BRIGHT);

		creditSprite = new AsciiSprite(new Point(0, TerminalDimensions.getNumLines() - credits.length), credits);
	}

	private static void createStartSprite() {
		String text = " > Press any key to start < ";
		startSprite = new AsciiSprite(
				new Point(TerminalDimensions.getNumColumns() / 2 - text.length() / 2,
						(int) Math.floor(TerminalDimensions.getNumLines() * .5f)),
				new String[] { Colorizer.colorize(text, TerminalForegroundColor.WHITE, TerminalBackgroundColor.BLACK,
						TerminalColorMode.BRIGHT) });
	}

}
