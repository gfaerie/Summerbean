package com.sonymobile.summerbean;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class SummerBean extends Activity {

	private GameView gameView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gameView = new GameView(this);
		setContentView(gameView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_summer_bean, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		gameView.resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		gameView.pause();
	}

}
