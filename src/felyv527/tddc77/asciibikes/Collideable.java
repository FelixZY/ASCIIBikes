package felyv527.tddc77.asciibikes;

import felyv527.tddc77.asciibikes.common.Rectangle;

public interface Collideable {
	
	Rectangle getCollisionRect();
	
	CollideableType getCollideableType();
	
	void onCollision(CollideableType collidedWithType);
}
