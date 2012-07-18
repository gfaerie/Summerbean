package com.sonymobile.summerbean;

public class Position {

	private double x;
	private double y;
	
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public Position change(Position p){
		x=x+p.x;
		y =y+p.y;
		return this;
	}
	
	
}
