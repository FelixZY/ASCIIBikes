package felyv527.tddc77.asciibikes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import felyv527.tddc77.asciibikes.common.Rectangle;

public class Arena {

	protected List<Obstacle> obstacles = new ArrayList<Obstacle>();

	public Arena() {
		addDefaultWalls();
	}
	
	public Arena(Obstacle... obstacles) {
		this(Arrays.asList(obstacles));
	}

	public Arena(List<Obstacle> obstacles) {
		addDefaultWalls();
		this.obstacles.addAll(obstacles);
	}

	public void createArena() {
		for (Obstacle obstacle : obstacles) {
			DrawHandler.registerForOverlayDraw(obstacle);
			CollisionHandler.registerForCollision(obstacle);
		}
	}

	public void unloadArena() {
		for (Obstacle obstacle : obstacles) {
			DrawHandler.unRegisterForOverlayDraw(obstacle);
			CollisionHandler.unRegisterForCollision(obstacle);
		}
	}

	private void addDefaultWalls() {
		// Left
		this.obstacles.add(new StaticObstacle(new Rectangle(UIInfo.GameArea
				.getLeft(), UIInfo.GameArea.getTop(), 1, UIInfo.GameArea
				.getHeight())));

		// Top
		this.obstacles.add(new StaticObstacle(new Rectangle(UIInfo.GameArea
				.getLeft() + 1, UIInfo.GameArea.getTop(), UIInfo.GameArea
				.getWidth() - 2, 1)));

		// Right
		this.obstacles.add(new StaticObstacle(new Rectangle(UIInfo.GameArea
				.getRight(), UIInfo.GameArea.getTop(), 1, UIInfo.GameArea
				.getHeight())));

		// Bottom
		this.obstacles.add(new StaticObstacle(new Rectangle(UIInfo.GameArea
				.getLeft() + 1, UIInfo.GameArea.getBottom(), UIInfo.GameArea
				.getWidth() - 2, 1)));
	}
}
