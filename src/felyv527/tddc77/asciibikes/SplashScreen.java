package felyv527.tddc77.asciibikes;

import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalColorMode;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.common.AsciiSprite;
import felyv527.tddc77.asciibikes.common.BackgroundSprite;
import felyv527.tddc77.asciibikes.common.Point;
import felyv527.tddc77.asciibikes.common.StringTools;
import felyv527.tddc77.asciibikes.io.InputManager;
import felyv527.tddc77.asciibikes.io.TerminalInfo;
import felyv527.tddc77.asciibikes.text.HeadlineFont;
import felyv527.tddc77.asciibikes.text.StandardFont;
import felyv527.tddc77.asciibikes.text.TextAlignment;

public class SplashScreen {

	private static final String CREDIT = "Created in 2016 by\nFelix Zedén Yverås (felyv527)\nIT1\n\n\nTDDC77 - Object oriented programming\nLinköping University";
	private static AsciiSprite backgroundSprite, titleSprite, creditSprite,
			startSprite;

	static {
		backgroundSprite = new BackgroundSprite(TerminalBackgroundColor.WHITE);
		createTitleSprite();
		createCreditSprite();
		createStartSprite();
	}

	public static void show() {
		// Vi vill bara exponera Show i SplashScreen!
		(new SplashGame()).Run();
	}

	private static class SplashGame extends Game {

		private static final int INIT_WAIT = 1000, BLINK_DELAY = 750;
		private int timer = 0;
		private boolean hasWaited = false;
		private boolean isBlinkVisible = false;
		private boolean requireDraw = true;

		@Override
		protected void prepare() {
			DrawHandler.startSession();

			DrawHandler.registerForDraw(backgroundSprite);
			DrawHandler.registerForOverlayDraw(titleSprite);
			DrawHandler.registerForOverlayDraw(creditSprite);
		}

		@Override
		protected void Update(long deltaMillis) {
			timer += deltaMillis;
			if (!hasWaited && timer >= INIT_WAIT) {
				hasWaited = true;
				timer -= INIT_WAIT + BLINK_DELAY;
				InputManager.getPressedKeys();
			}

			if (hasWaited) {
				if (!InputManager.getPressedKeys().isEmpty()) {
					this.Exit();
				} else {
					if (timer >= BLINK_DELAY) {
						requireDraw = true;
						isBlinkVisible = !isBlinkVisible;
						if (isBlinkVisible)
							DrawHandler.registerForOverlayDraw(startSprite);
						else
							DrawHandler.unRegisterForOverlayDraw(startSprite);
					}

					timer %= BLINK_DELAY;
				}
			}
		}

		@Override
		protected void Exit() {
			DrawHandler.unRegisterForDraw(backgroundSprite);
			DrawHandler.unRegisterForOverlayDraw(titleSprite);
			DrawHandler.unRegisterForOverlayDraw(creditSprite);
			DrawHandler.unRegisterForOverlayDraw(startSprite);
			DrawHandler.endSession();
			super.Exit();
		}

		@Override
		protected void Draw() {
			if (requireDraw) {
				DrawHandler.drawFrame();
				requireDraw = false;
			}
		}
	};

	private static void createTitleSprite() {
		String[] title = (new HeadlineFont()).applyFont("ASCIIBikes",
				TextAlignment.CENTER);

		title = StringTools.center(title, TerminalInfo.getTerminalCols());

		String[] colorizedTitle = Colorizer.colorize(title,
				TerminalForegroundColor.YELLOW, TerminalBackgroundColor.BLACK,
				TerminalColorMode.BRIGHT);

		titleSprite = new AsciiSprite(new Point(0, 2), colorizedTitle);
	}

	private static void createCreditSprite() {
		String[] credits = (new StandardFont()).applyFont(CREDIT,
				TextAlignment.CENTER);

		credits = StringTools.center(credits, TerminalInfo.getTerminalCols());

		credits[0] = Colorizer.colorize(credits[0],
				TerminalForegroundColor.BLACK, TerminalBackgroundColor.YELLOW);

		credits[1] = Colorizer.colorize(credits[1],
				TerminalForegroundColor.WHITE, TerminalBackgroundColor.MAGENTA,
				TerminalColorMode.BRIGHT);

		credits[2] = Colorizer.colorize(credits[2],
				TerminalForegroundColor.BLACK, TerminalBackgroundColor.YELLOW);

		credits[3] = Colorizer.colorize(credits[3],
				TerminalBackgroundColor.WHITE);
		credits[4] = Colorizer.colorize(credits[4],
				TerminalBackgroundColor.WHITE);

		credits[5] = Colorizer.colorize(credits[5],
				TerminalForegroundColor.WHITE, TerminalBackgroundColor.CYAN);

		credits[6] = Colorizer.colorize(credits[6],
				TerminalForegroundColor.WHITE, TerminalBackgroundColor.CYAN,
				TerminalColorMode.BRIGHT);

		creditSprite = new AsciiSprite(new Point(0,
				TerminalInfo.getTerminalLines() - credits.length), credits);
	}

	private static void createStartSprite() {
		String text = " > Press any key to start < ";
		startSprite = new AsciiSprite(new Point(TerminalInfo.getTerminalCols()
				/ 2 - text.length() / 2, (int) Math.floor(TerminalInfo
				.getTerminalLines() * .5f)), new String[] { Colorizer.colorize(
				text, TerminalForegroundColor.YELLOW,
				TerminalBackgroundColor.BLACK, TerminalColorMode.BRIGHT) });
	}

}
