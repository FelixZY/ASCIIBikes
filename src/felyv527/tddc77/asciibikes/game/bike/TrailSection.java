package felyv527.tddc77.asciibikes.game.bike;

import felyv527.tddc77.asciibikes.Point;
import felyv527.tddc77.asciibikes.Rectangle;
import felyv527.tddc77.asciibikes.StringTools;
import felyv527.tddc77.asciibikes.collision.Collideable;
import felyv527.tddc77.asciibikes.collision.CollideableType;
import felyv527.tddc77.asciibikes.collision.CollisionManager;
import felyv527.tddc77.asciibikes.colors.Colorizer;
import felyv527.tddc77.asciibikes.colors.TerminalBackgroundColor;
import felyv527.tddc77.asciibikes.drawing.AsciiSprite;
import felyv527.tddc77.asciibikes.drawing.DrawManager;
import felyv527.tddc77.asciibikes.drawing.Drawable;
import felyv527.tddc77.asciibikes.game.Direction;

class TrailSection implements Drawable, Collideable {

	final Direction growDirection;

	private Point origin;
	private String[] sprite;
	private int trailLength = 0;
	private TerminalBackgroundColor trailColor;

	public TrailSection(Point origin, Direction growDirection, TerminalBackgroundColor trailColor) {
		this.origin = origin;
		this.growDirection = growDirection;
		this.trailColor = trailColor;

		updateTrailLength(1);
		DrawManager.registerForDraw(this);
		CollisionManager.registerForCollision(this);
	}

	public int length() {
		return trailLength;
	}

	public void expand() {
		if (growDirection == Direction.LEFT)
			origin.setX(origin.getX() - 1);
		else if (growDirection == Direction.UP)
			origin.setY(origin.getY() - 1);
		updateTrailLength(length() + 1);
	}

	public void shrink() {
		if (length() > 0) {
			if (growDirection == Direction.DOWN)
				origin.setY(origin.getY() + 1);
			else if (growDirection == Direction.RIGHT)
				origin.setX(origin.getX() + 1);

			updateTrailLength(length() - 1);
		}
	}

	private void updateTrailLength(int length) {
		trailLength = length;
		if (growDirection == Direction.LEFT || growDirection == Direction.RIGHT) {
			this.sprite = new String[] { Colorizer.colorize(StringTools.repeat(' ', trailLength), trailColor) };
		} else {
			this.sprite = new String[trailLength];
			String colorized = Colorizer.colorize(" ", trailColor);

			for (int i = 0; i < sprite.length; i++) {
				sprite[i] = colorized;
			}
		}
	}

	public void unload() {
		DrawManager.unRegisterForDraw(this);
		CollisionManager.unRegisterForCollision(this);
	}

	@Override
	public AsciiSprite getAsciiSprite() {
		return new AsciiSprite(origin, sprite);
	}

	@Override
	public Rectangle getCollisionRect() {
		if (growDirection == Direction.UP || growDirection == Direction.DOWN)
			return new Rectangle(origin.getX(), origin.getY(), 1, this.length());
		else
			return new Rectangle(origin.getX(), origin.getY(), this.length(), 1);
	}

	@Override
	public CollideableType getCollideableType() {
		return CollideableType.WALL;
	}

	@Override
	public void onCollision(CollideableType collidedWithType) {
	}
}