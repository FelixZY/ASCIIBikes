package felyv527.tddc77.asciibikes.game.config;

import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.io.Keys;

/**
 * Contains basic information on what controls to use for controlling a bike as
 * well as basic information about a bike such as name and color.
 *
 */
public final class KeyboardPlayerInfo extends BikeInfo {

	private Keys turnLeftKey;

	private Keys turnRightKey;

	protected KeyboardPlayerInfo() {
		super();
	}

	protected KeyboardPlayerInfo(String name, TerminalForegroundColor color, int trailLength, Keys turnLeftKey,
			Keys turnRightKey) {
		super(name, color, trailLength);
		setTurnLeftKey(turnLeftKey);
		setTurnRightKey(turnRightKey);
	}

	protected void setTurnLeftKey(Keys turnLeftKey) {
		this.turnLeftKey = turnLeftKey;
	}

	protected void setTurnRightKey(Keys turnRightKey) {
		this.turnRightKey = turnRightKey;
	}

	public Keys getTurnLeftKey() {
		return this.turnLeftKey;
	}

	public Keys getTurnRightKey() {
		return this.turnRightKey;
	}

}
