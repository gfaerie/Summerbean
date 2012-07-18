package com.sonymobile.summerbean;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
	private Map<Integer, Bitmap> gameObjectsBitMaps;
	private Bitmap background;
	private Bitmap spelare;
	private GameEngine gameEngine;
	private Random random = new Random();
	private Timer timer;

	public GameView(Context context) {
		super(context);
		initBitmaps();

	}

	private void initBitmaps() {
		background = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.strand);
		this.gameObjectsBitMaps = new HashMap<Integer, Bitmap>();
		this.gameObjectsBitMaps.put(GameObject.GAME_OBJECT_MOLN, BitmapFactory
				.decodeResource(this.getResources(), R.drawable.moln));
		this.gameObjectsBitMaps.put(GameObject.GAME_OBJECT_BEAN, BitmapFactory
				.decodeResource(this.getResources(), R.drawable.bean_small));
		this.gameObjectsBitMaps.put(GameObject.GAME_OBJECT_REGN, BitmapFactory
				.decodeResource(this.getResources(), R.drawable.regn));
		this.gameObjectsBitMaps.put(GameObject.GAME_OBJECT_PLAYER, BitmapFactory
				.decodeResource(this.getResources(), R.drawable.xperia_small));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		updateGame();
		drawGame(canvas);
	}

	public void resume() {
		timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				postInvalidate();
			}
		};
		timer.schedule(task, 1000, 10);
	}

	public void pause() {
		timer.cancel();
		timer = null;
	}

	public void updateGame() {
		gameEngine.update(1);
	}

	public void drawGame(Canvas canvas) {
		if (gameEngine != null) {
			Paint paint = new Paint();
			
			canvas.drawBitmap(background, null, new RectF(0f,0f,(float)canvas.getWidth(),(float)canvas.getHeight()), null);
			for (Entry<GameObject, GameObjectMetadata> gameObject : gameEngine
					.getGameObjects().entrySet()) {
				canvas.drawBitmap(gameObjectsBitMaps.get(gameObject.getKey()
						.getGraphicsId()), (long) (gameObject.getValue().getPosition()
						.getX()
						- gameObject.getKey().getxSize()), (long) (gameObject.getValue()
						.getPosition().getY()
						- gameObject.getKey().getySize()), paint);
			}
			paint.setColor(Color.BLACK);
			canvas.drawText("Score: "+gameEngine.getScore(), 0, gameEngine.getySize()-50, paint);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (gameEngine == null) {
			int canvasWidth = MeasureSpec.getSize(widthMeasureSpec);
			int canvasHeight = MeasureSpec.getSize(heightMeasureSpec);
			gameEngine = new GameEngine(canvasWidth, canvasHeight);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("sb", "x: " + event.getX() + " y:" + event.getY());
		Position playerPosition = gameEngine.getPlayerPosition();
		double xSpeed  = event.getX()-playerPosition.getX();
		double ySpeed  = event.getY()-playerPosition.getY();

		if (event.getAction() == MotionEvent.ACTION_DOWN && gameEngine != null) {
			gameEngine.addObject(new GameObject(GameObject.GAME_OBJECT_BEAN, -1, 10,
					10),
					new Position(playerPosition.getX(), playerPosition.getY()),
					new LinearMovement(xSpeed/20.0, ySpeed/20.0));
		}

		return super.onTouchEvent(event);
	}

}