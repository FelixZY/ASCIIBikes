package felyv527.tddc77.asciibikes.game.config;

import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;

/**
 * Contains basic information about a bike such as name and color.
 *
 */
public class BikeInfo {
	private String name;

	private int trailLength;

	private TerminalForegroundColor color;

	protected BikeInfo() {

	}

	protected BikeInfo(String name, TerminalForegroundColor color, int trailLength) {
		setName(name);
		setColor(color);
		setTrailLength(trailLength);
	}

	public final String getName() {
		return this.name;
	}

	public final int getTrailLength() {
		return this.trailLength;
	}

	public final TerminalForegroundColor getForegroundColor() {
		return this.color;
	}

	public final TerminalBackgroundColor getBackgroundColor() {
		return TerminalBackgroundColor.fromForegroundColor(this.color);
	}

	protected final void setName(String name) {
		this.name = name;
	}

	protected final void setTrailLength(int trailLength) {
		this.trailLength = trailLength;
	}

	protected final void setColor(TerminalForegroundColor color) {
		this.color = color;
	}

}
