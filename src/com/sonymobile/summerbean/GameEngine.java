package com.sonymobile.summerbean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;
import java.util.Set;

public class GameEngine {

	private AtomicInteger atomicInteger = new AtomicInteger();
	private Random random = new Random();
	private long xSize;
	private long ySize;
	private boolean gameOver = false;
	private Map<GameObject, GameObjectMetadata> gameObjects = new HashMap<GameObject, GameObjectMetadata>();
	private CollisionDetector collisionDetector = new CollisionDetector();
	private GameObject player;

	public GameEngine(long xSize, long ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
		addPlayer();
	}
	
	private void addPlayer(){
		this.player = new GameObject(GameObject.GAME_OBJECT_PLAYER, 0, 35, 45);
		gameObjects.put(player, new GameObjectMetadata(null, new Position(
				xSize / 2, (long) (ySize * 0.9))));
	}

	public void addObject(GameObject object, Position position,
			Movement movement) {
		gameObjects.put(object, new GameObjectMetadata(movement, position));
	}

	public Map<GameObject, GameObjectMetadata> getGameObjects() {
		return gameObjects;
	}

	public void update(long weight) {
		Set<GameObject> toBeRemoved = new HashSet<GameObject>();

		addClouds();
		dropRain();

		for (Entry<GameObject, GameObjectMetadata> entry : gameObjects
				.entrySet()) {
			if (entry.getValue().getMovement() != null) {
				Position change = entry.getValue().getMovement()
						.getChange(weight);
				Position newPosition = entry.getValue().getPosition()
						.change(change);

				if (newPosition.getX() > (xSize + 100)
						|| newPosition.getX() < -100
						|| newPosition.getY() < -100
						|| newPosition.getY() > (ySize + 100)) {
					if(entry.getKey().getCategory()==1){
reset();
}
					toBeRemoved.add(entry.getKey());
				}
			}
		}
		toBeRemoved.addAll(collisionDetector.getCollision(gameObjects));
		for (GameObject gameObject : toBeRemoved) {
			if(gameObject.getCategory()==1){
				atomicInteger.incrementAndGet();
			}
			gameObjects.remove(gameObject);
		}
	}

	public void addClouds() {
		if (random.nextInt(100) > 96) {
			long startY = random.nextInt(100) - 50;
			long startX = random.nextBoolean() ? -100 : xSize + 100;
			long xSpeed = (startX > 0 ? -1 : 1) * (random.nextInt(5) + 1);
			GameObject gameObject = new GameObject(GameObject.GAME_OBJECT_MOLN,
					0, 1, 1);
			Position position = new Position(startX, startY);
			Movement movement = new LinearMovement(xSpeed, 0);
			gameObjects.put(gameObject, new GameObjectMetadata(movement,
					position));
		}
	}

	public void dropRain() {
		Map<GameObject, GameObjectMetadata> newObjects = new HashMap<GameObject, GameObjectMetadata>();

		for (Entry<GameObject, GameObjectMetadata> entry : gameObjects
				.entrySet()) {
			if (entry.getKey().getGraphicsId() == GameObject.GAME_OBJECT_MOLN) {
				if (random.nextInt(1000) > 985) {
					long ySpeed = (random.nextInt(7) + 1);
					GameObject gameObject = new GameObject(
							GameObject.GAME_OBJECT_REGN, 1, 25, 25);
					Position position = new Position(entry.getValue()
							.getPosition().getX(), entry.getValue()
							.getPosition().getY() + 30);
					Movement movement = new LinearMovement(0, ySpeed);
					newObjects.put(gameObject, new GameObjectMetadata(movement,
							position));
				}
			}
		}
		gameObjects.putAll(newObjects);
	}

	public long getxSize() {
		return xSize;
	}

	public long getySize() {
		return ySize;
	}
	
	public Position getPlayerPosition(){
		return gameObjects.get(player).getPosition();
	}
	
	public int getScore(){
		return atomicInteger.get();
	}
	
	public boolean isGameOver(){
		return gameOver;
	}
	
	public void reset(){
		atomicInteger.set(0);
	}

}
