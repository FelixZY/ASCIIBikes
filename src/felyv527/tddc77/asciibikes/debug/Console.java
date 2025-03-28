package felyv527.tddc77.asciibikes.debug;

import java.util.ArrayDeque;
import java.util.Queue;

import felyv527.tddc77.asciibikes.Drawable;
import felyv527.tddc77.asciibikes.DrawHandler;
import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalColorMode;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.common.AsciiSprite;
import felyv527.tddc77.asciibikes.common.Point;
import felyv527.tddc77.asciibikes.io.TerminalInfo;

public class Console implements Drawable {

	private static String[] outData = new String[0];
	private static Queue<String> dataQueue = new ArrayDeque<String>();
	private static final int DISPLAY_DURATION = 1500;
	private static int currentDisplay = 0;
	private static boolean isDisplaying = false;

	private static Console self;

	public static void init() {
		if (self != null)
			unload();
		self = new Console();
		DrawHandler.registerForFrontDraw(self);
	}

	public static void update(long deltaMillis) {
		if (isDisplaying) {
			currentDisplay += deltaMillis;
			if (currentDisplay > DISPLAY_DURATION) {
				outData = new String[0];
				isDisplaying = false;
			}
		} else if (!dataQueue.isEmpty()) {
			outData = new String[Math.min(16, dataQueue.size())];

			for (int i = 0; i < outData.length; i++) {
				outData[i] = Colorizer
						.colorize(dataQueue.poll(),
								TerminalForegroundColor.GREEN,
								TerminalBackgroundColor.BLACK,
								TerminalColorMode.BRIGHT);
			}

			isDisplaying = true;
			currentDisplay = 0;
		}
	}

	public static void unload() {
		if (self != null)
			DrawHandler.unRegisterForFrontDraw(self);
	}

	public static void println(String message) {
		dataQueue.add(message);
	}

	@Override
	public AsciiSprite getAsciiSprite() {
		return new AsciiSprite(new Point(0, TerminalInfo.getTerminalLines()
				- outData.length), outData);
	}
}