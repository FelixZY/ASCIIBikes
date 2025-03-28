package felyv527.tddc77.asciibikes.common;

import felyv527.tddc77.asciibikes.Drawable;

/**
 * Class to draw simple sprites.
 * 
 * This can be registrated with DrawHandler but does not manage DrawHandler
 * registration itself
 */
public class AsciiSprite implements Drawable {

	private Point location;
	private String[] sprite;

	public AsciiSprite(Point location, String[] sprite) {
		this.location = location;
		this.sprite = sprite;
	}

	@Override
	public AsciiSprite getAsciiSprite() {
		return this;
	}

	public Point getSpriteLocation() {
		return location;
	}
	
	public String[] getStringRepresentation() {
		return sprite;
	}

}
