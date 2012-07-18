package com.sonymobile.summerbean;

public class LinearMovement implements Movement {

	private double xSpeed;
	private double ySpeed;
	
	public LinearMovement(double xSpeed, double ySpeed) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}

	public Position getChange(double weight) {
		return new Position(xSpeed*weight, ySpeed*weight);
	}

}
