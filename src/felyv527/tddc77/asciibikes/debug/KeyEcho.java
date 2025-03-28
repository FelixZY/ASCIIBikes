package felyv527.tddc77.asciibikes.debug;

import java.util.Set;

import felyv527.tddc77.asciibikes.Drawable;
import felyv527.tddc77.asciibikes.DrawHandler;
import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.common.AsciiSprite;
import felyv527.tddc77.asciibikes.common.Point;
import felyv527.tddc77.asciibikes.io.Keys;
import felyv527.tddc77.asciibikes.io.TerminalInfo;
import felyv527.tddc77.asciibikes.text.StandardFont;

public class KeyEcho implements Drawable {

	private static String[] outData = new String[] { "Pressed: "
			+ Keys.NONE.toString() };
	private static String longest = outData[0];
	private static KeyEcho self;

	public static void init() {
		if (self != null)
			unload();
		self = new KeyEcho();
		DrawHandler.registerForFrontDraw(self);
	}

	public static void update(Set<Keys> keys) {
		if (!keys.isEmpty()) {
			StringBuilder strb = new StringBuilder();

			for (Keys key : keys) {
				strb.append("Pressed: " + key.toString() + "\n");
			}

			strb.deleteCharAt(strb.length() - 1);

			String[] data = new StandardFont().applyFont(strb.toString());

			longest = "";

			for (int i = 0; i < data.length; i++) {
				if (data[i].length() > longest.length())
					longest = data[i];
				data[i] = Colorizer.colorize(data[i],
						TerminalForegroundColor.WHITE,
						TerminalBackgroundColor.RED);
			}

			outData = data;
		}
	}

	public static void unload() {
		if (self != null)
			DrawHandler.unRegisterForFrontDraw(self);
	}

	@Override
	public AsciiSprite getAsciiSprite() {
		return new AsciiSprite(new Point(TerminalInfo.getTerminalCols()
				- longest.length(), TerminalInfo.getTerminalLines()
				- outData.length), outData);
	}

}
