package felyv527.tddc77.asciibikes.collision;

/**
 * {@link CollisionData} represents two intersecting {@link Collideable}s
 *
 */
final class CollisionData {
	public final Collideable collideable1, collideable2;

	public CollisionData(Collideable collideable1, Collideable collideable2) {
		this.collideable1 = collideable1;
		this.collideable2 = collideable2;
	}
}
