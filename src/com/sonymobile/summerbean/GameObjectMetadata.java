package com.sonymobile.summerbean;

public class GameObjectMetadata {

	private Movement movement;
	private Position position;
	
	public GameObjectMetadata(Movement movement, Position position) {
		this.movement = movement;
		this.position = position;
	}

	public Movement getMovement() {
		return movement;
	}

	public Position getPosition() {
		return position;
	}

	
}
