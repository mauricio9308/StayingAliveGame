package com.stayingalive.stayingaliveapp.android;

import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.stayingalive.stayingaliveapp.StayingAliveGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new StayingAliveGame(), config);
	}
}
