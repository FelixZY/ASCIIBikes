package felyv527.tddc77.asciibikes.debug;

import felyv527.tddc77.asciibikes.Drawable;
import felyv527.tddc77.asciibikes.DrawHandler;
import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.common.AsciiSprite;
import felyv527.tddc77.asciibikes.common.Point;
import felyv527.tddc77.asciibikes.io.TerminalInfo;

public class FpsCounter implements Drawable {

	private static String[] outData;
	private static int longest = 0;
	private static FpsCounter self;

	public static void init() {
		if (self != null)
			unload();
		self = new FpsCounter();
		DrawHandler.registerForFrontDraw(self);
	}

	public static void update(long deltaMillis) {
		deltaMillis = Math.max(deltaMillis, 1);
		String out = "FPS: " + (1000 / deltaMillis) + " | DeltaMillis: "
				+ deltaMillis;
		longest = out.length();
		outData = new String[] { Colorizer.colorize(out,
				TerminalForegroundColor.GREEN, TerminalBackgroundColor.BLACK) };
	}

	public static void unload() {
		if (self != null)
			DrawHandler.unRegisterForFrontDraw(self);
	}

	@Override
	public AsciiSprite getAsciiSprite() {
		return new AsciiSprite(new Point(TerminalInfo.getTerminalCols()
				- longest, 0), outData);
	}

}
