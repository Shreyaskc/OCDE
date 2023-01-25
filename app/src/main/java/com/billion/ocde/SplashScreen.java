package com.billion.ocde;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_splash_screen);
	Thread timer = new Thread() {
	    @Override
	    public void run() {
		try {
		    sleep(3000);
		} catch (InterruptedException e) {

		} finally {
		    try {
			Intent intent = new Intent(getBaseContext(),
				MainActivity.class);
			startActivity(intent);
		    } catch (Exception e2) {
			MainActivity.printLog(MainActivity
				.getExceptionAsString(e2));
			Log.e("", e2.getMessage());
		    }

		}

		super.run();
	    }
	};
	timer.start();
    }
}
