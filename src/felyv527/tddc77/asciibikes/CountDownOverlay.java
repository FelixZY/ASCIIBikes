package felyv527.tddc77.asciibikes;

import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.common.AsciiSprite;
import felyv527.tddc77.asciibikes.common.Point;
import felyv527.tddc77.asciibikes.common.StringTools;
import felyv527.tddc77.asciibikes.io.TerminalInfo;
import felyv527.tddc77.asciibikes.text.HeadlineFont;
import felyv527.tddc77.asciibikes.text.TextAlignment;

public class CountDownOverlay implements Drawable {

	public interface CountDownCompletedListener {
		void onCountDownCompleted();
	}

	private static final Point screenLocation = new Point(new Point(0, TerminalInfo.getTerminalLines() / 2
			- (new HeadlineFont()).applyFont("A").length / 2));

	private final int waitTime;
	private long startTime, targetTime;
	private CountDownCompletedListener onCountDownCompletedListener;

	public CountDownOverlay(int milliseconds) {
		this.waitTime = milliseconds;
	}

	public void start(CountDownCompletedListener onCountDownCompletedListener) {
		DrawHandler.registerForFrontDraw(this);
		startTime = System.nanoTime() / 1000000;
		targetTime = startTime + waitTime;
		this.onCountDownCompletedListener = onCountDownCompletedListener;
	}

	public void update(long deltaMillis) {
		startTime += deltaMillis;
		if (startTime >= targetTime) {
			DrawHandler.unRegisterForFrontDraw(this);
			onCountDownCompletedListener.onCountDownCompleted();
		}
	}

	@Override
	public AsciiSprite getAsciiSprite() {
		return new AsciiSprite(screenLocation, getStringRepresentation());
	}

	private String[] getStringRepresentation() {
		String[] representation = (new HeadlineFont()).applyFont(
				String.valueOf((int) Math.ceil((targetTime - startTime) / 1000f)), TextAlignment.CENTER);
		representation = StringTools.center(representation, TerminalInfo.getTerminalCols());
		representation = Colorizer.colorize(representation, TerminalForegroundColor.YELLOW,
				TerminalBackgroundColor.BLACK);
		return representation;
	}

}
