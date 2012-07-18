package com.sonymobile.summerbean;

public class GameObject {

	public static final int GAME_OBJECT_MOLN = 0;
	public static final int GAME_OBJECT_BEAN = 1;
	public static final int GAME_OBJECT_REGN = 2;
	public static final int GAME_OBJECT_PLAYER = 3;

	private int graphicsId;
	private int category;
	private int xSize;
	private int ySize;

	public GameObject(int graphicsId, int category, int xSize, int ySize) {
		this.graphicsId = graphicsId;
		this.category = category;
		this.xSize = xSize;
		this.ySize = ySize;
	}

	public int getCategory() {
		return category;
	}

	public int getGraphicsId() {
		return graphicsId;
	}

	public int getxSize() {
		return xSize;
	}

	public int getySize() {
		return ySize;
	}
	
}
