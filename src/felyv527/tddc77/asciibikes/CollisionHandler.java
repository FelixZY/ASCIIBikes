package felyv527.tddc77.asciibikes;

import java.util.ArrayList;
import java.util.List;

public class CollisionHandler {

	private static List<Collideable> collisionItems = new ArrayList<>();
	
	public static int debugGetItemCount() {
		return collisionItems.size();
	}

	public static void registerForCollision(Collideable collideable) {
		collisionItems.add(collideable);
	}

	public static void unRegisterForCollision(Collideable collideable) {
		collisionItems.remove(collideable);
	}
	
	public static void clean() {
		collisionItems.clear();
	}

	public static void runCollisionCheck() {
		List<CollisionData> collidedItems = new ArrayList<>();

		for (int i = 0; i < collisionItems.size() - 1; i++) {
			for (int j = i + 1; j < collisionItems.size(); j++) {
				if (collisionItems.get(i).getCollisionRect()
						.intersects(collisionItems.get(j).getCollisionRect())) {
					collidedItems.add(new CollisionData(collisionItems.get(i),
							collisionItems.get(j)));
				}
			}
		}

		for (CollisionData cData : collidedItems) {
			cData.collideable1.onCollision(cData.collideable2
					.getCollideableType());
			cData.collideable2.onCollision(cData.collideable1
					.getCollideableType());
		}
	}

	private static class CollisionData {
		public Collideable collideable1, collideable2;

		public CollisionData(Collideable collideable1, Collideable collideable2) {
			this.collideable1 = collideable1;
			this.collideable2 = collideable2;
		}
	}
}
