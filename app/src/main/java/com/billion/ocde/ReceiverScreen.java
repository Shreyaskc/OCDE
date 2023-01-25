package com.billion.ocde;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.PowerManager;

public class ReceiverScreen extends BroadcastReceiver {

    PowerManager.WakeLock wakeLock;
    public static long TimerIs;
    public static CountDownTimer timer;

    PowerManager pm;

    @SuppressWarnings("deprecation")
    @Override
    public void onReceive(Context context, Intent intent) {
	// TODO Auto-generated method stub

	pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	if ((intent.getAction().equals(Intent.ACTION_SCREEN_OFF))) {

	}
    }
}
