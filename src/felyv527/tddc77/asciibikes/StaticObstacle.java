package felyv527.tddc77.asciibikes;

import felyv527.tddc77.asciibikes.common.AsciiSprite;
import felyv527.tddc77.asciibikes.common.Point;
import felyv527.tddc77.asciibikes.common.Rectangle;

public class StaticObstacle extends Obstacle {

	private Rectangle extent;
	private AsciiSprite sprite;

	public StaticObstacle(Rectangle extent) {
		this.extent = extent;
		this.sprite = new AsciiSprite(new Point(extent.getX(), extent.getY()),
				this.getStringRepresentation(extent));
	}

	@Override
	public Rectangle getCollisionRect() {
		return extent;
	}

	@Override
	public AsciiSprite getAsciiSprite() {
		return sprite;
	}
}
