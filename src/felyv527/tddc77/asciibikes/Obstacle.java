package felyv527.tddc77.asciibikes;

import felyv527.tddc77.asciibikes.colors.TerminalColorMode;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.common.Rectangle;
import felyv527.tddc77.asciibikes.common.StringTools;

public abstract class Obstacle implements Collideable, Drawable {

	protected String[] getStringRepresentation(Rectangle obstacleExtent) {

		return Colorizer.colorize(StringTools.frame(obstacleExtent, 0),
				UIInfo.obstacleForeground, UIInfo.obstacleBackground,
				TerminalColorMode.BRIGHT);
	}

	@Override
	public CollideableType getCollideableType() {
		return CollideableType.OBSTACLE;
	}

	@Override
	public void onCollision(CollideableType collidedWithType) {

	}

}
