package felyv527.tddc77.asciibikes.collision;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link CollisionManager} manages all collisions in ASCIIBikes.
 * 
 * <p>
 * All objects requiring collision detection must register a {@link Collideable}
 * which will recieve a callback if there is a collision.
 *
 */
public class CollisionManager {

	private static List<Collideable> collisionItems = new ArrayList<>();

	private CollisionManager() {
		// static class. Should not be instantiated.

		/*
		 * There is reasoning to be done over whether this class should be
		 * static or not. In this case I have chosen to keep it static because
		 * it makes it easier for collision items to tend to themselves and
		 * because that the game is quite small.
		 */
	}

	/**
	 * Registers a {@link Collideable} for collision detection.
	 * 
	 * @param collideable
	 *            The collideable to register
	 */
	public static void registerForCollision(Collideable collideable) {
		if (!collisionItems.contains(collideable))
			collisionItems.add(collideable);
	}

	/**
	 * Unregisters a {@link Collideable} from collision detection.
	 * 
	 * @param collideable
	 *            The collideable to unregister
	 */
	public static void unRegisterForCollision(Collideable collideable) {
		if (collisionItems.contains(collideable))
			collisionItems.remove(collideable);
	}

	/**
	 * Unregisters all currently registered {@link Collideable}s from collision
	 * detection.
	 */
	public static void unRegisterAll() {
		collisionItems.clear();
	}

	/**
	 * Performs a collision detection sweep.
	 * 
	 * <p>
	 * If any {@link Collideable}s are colliding they will be notified.
	 */
	public static void runCollisionCheck() {
		List<CollisionData> collidedItems = new ArrayList<>();

		for (int i = 0; i < collisionItems.size() - 1; i++) {
			for (int j = i + 1; j < collisionItems.size(); j++) {
				if (collisionItems.get(i).getCollisionRect().intersects(collisionItems.get(j).getCollisionRect())) {
					collidedItems.add(new CollisionData(collisionItems.get(i), collisionItems.get(j)));
				}
			}
		}

		/*
		 * We want to be sure that collision detection can happen without
		 * interruption. Therefore we do not notify any {@link Collideable}s of
		 * their collision until all checks are done.
		 */
		for (CollisionData cData : collidedItems) {
			cData.collideable1.onCollision(cData.collideable2.getCollideableType());
			cData.collideable2.onCollision(cData.collideable1.getCollideableType());
		}
	}
}
