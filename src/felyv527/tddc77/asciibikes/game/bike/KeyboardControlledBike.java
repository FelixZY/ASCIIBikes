package felyv527.tddc77.asciibikes.game.bike;

import java.util.Set;

import felyv527.tddc77.asciibikes.game.config.KeyboardPlayerInfo;
import felyv527.tddc77.asciibikes.io.Keys;

/**
 * A {@link Bike} controlled using keyboard input
 * 
 */
public class KeyboardControlledBike extends Bike {

	private Keys turnLeft, turnRight;

	/**
	 * Constructs and initializes a new {@link KeyboardControlledBike} with the
	 * specified {@code bikeId} and {@code playerConfig}
	 * 
	 * @param playerIndex
	 *            The index of the player controlling this bike.
	 * @param bikeInfo
	 *            Basic information about this bike
	 */
	public KeyboardControlledBike(int bikeId, KeyboardPlayerInfo playerConfig) {
		super(bikeId, playerConfig);
		this.turnLeft = playerConfig.getTurnLeftKey();
		this.turnRight = playerConfig.getTurnRightKey();
	}

	@Override
	protected void onUpdate(Set<Keys> pressedKeys, long deltaMillis) {
		if (pressedKeys.contains(turnLeft)) {
			this.turnLeft();
		}

		if (pressedKeys.contains(turnRight)) {
			this.turnRight();
		}
	}
}
