package felyv527.tddc77.asciibikes.game.config;

import felyv527.tddc77.asciibikes.game.GameWindowAreas;

/**
 * Provides enum values for some specific trail lengths.
 * 
 * <p>
 * Originally created to make trail length selection possible using a
 * {@link EnumSelectionMenu}
 *
 */
enum TrailLength {
	DEFAULT((int) (GameWindowAreas.GameArea.getArea() * .045f)), //
	LONG((int) (GameWindowAreas.GameArea.getArea() * .08f)), //
	SHORT((int) (GameWindowAreas.GameArea.getArea() * .015f)), CUSTOM(0);

	final int length;

	TrailLength(int length) {
		this.length = length;
	}
}
