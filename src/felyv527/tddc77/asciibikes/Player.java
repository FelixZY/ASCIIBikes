package felyv527.tddc77.asciibikes;

import java.util.Set;

import felyv527.tddc77.asciibikes.colors.TerminalForegroundColor;
import felyv527.tddc77.asciibikes.common.Direction;
import felyv527.tddc77.asciibikes.common.Point;
import felyv527.tddc77.asciibikes.io.Keys;

public class Player {
	public final String NAME;
	public final TerminalForegroundColor COLOR;

	private Bike bike;
	private Keys turnLeft, turnRight;

	public Player(String name, TerminalForegroundColor playerColor) {
		this.NAME = name;
		this.COLOR = playerColor;
	}

	public void setKeys(Keys turnLeft, Keys turnRight) {
		this.turnLeft = turnLeft;
		this.turnRight = turnRight;
	}

	// TODO: private
	public void createBike(Point spawnLocation, Direction spawnDirection,
			int trailLength) {
		if (bike != null) {
			bike.unload();
		}

		this.bike = new Bike(COLOR, spawnLocation, spawnDirection, trailLength);
	}

	public void update(Set<Keys> keys, long deltaMillis) {
		if (bike.isAlive()) {
			if (keys.contains(turnLeft)) {
				bike.turnLeft();
			}

			if (keys.contains(turnRight)) {
				bike.turnRight();
			}
		}

		bike.update(deltaMillis);
	}

	public void unload() {
		if (bike != null) {
			bike.unload();
		}
	}

}
