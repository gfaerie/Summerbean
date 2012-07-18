package com.sonymobile.summerbean;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CollisionDetector {

	public Collection<GameObject> getCollision(
			Map<GameObject, GameObjectMetadata> gameObjects) {
		Set<GameObject> collisions = new HashSet<GameObject>();
		for (Entry<GameObject, GameObjectMetadata> one : gameObjects.entrySet()) {
			for (Entry<GameObject, GameObjectMetadata> two : gameObjects
					.entrySet()) {
				if (isCollide(one, two)) {
					collisions.add(one.getKey());
					collisions.add(two.getKey());
				}
			}
		}
		return collisions;
	}

	public boolean isCollide(Entry<GameObject, GameObjectMetadata> one,
			Entry<GameObject, GameObjectMetadata> two) {
		if (one.getKey().getCategory() != 0 && two.getKey().getCategory() != 0
				&& one.getKey().getCategory() != two.getKey().getCategory()) {
			double xDistance = Math.abs(one.getValue().getPosition().getX()
					- two.getValue().getPosition().getX());
			double yDistance = Math.abs(one.getValue().getPosition().getY()
					- two.getValue().getPosition().getY());
			double minXDistance = one.getKey().getxSize()
					+ two.getKey().getxSize();
			double minYDistance = one.getKey().getySize()
					+ two.getKey().getySize();
			return xDistance < minXDistance && yDistance < minYDistance;
		}
		return false;
	}
}
