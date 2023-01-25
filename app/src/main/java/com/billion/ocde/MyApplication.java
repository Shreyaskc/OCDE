package com.billion.ocde;

import java.io.File;
import java.io.IOException;

import android.app.Application;
import android.os.Environment;

public class MyApplication extends Application {

    public void onCreate() {
	super.onCreate();

	if (isExternalStorageWritable()) {

	    File appDirectory = new File(
		    Environment.getExternalStorageDirectory() + "/OCDE");
	    File logDirectory = new File(appDirectory + "/log");
	    File logFile = new File(logDirectory, "logcat" + ".txt");

	    // create app folder
	    if (!appDirectory.exists()) {
		appDirectory.mkdir();
	    }

	    // create log folder
	    if (!logDirectory.exists()) {
		logDirectory.mkdir();
	    }

	    // clear the previous logcat and then write the new one to the file
	    try {
		Process process = Runtime.getRuntime().exec("logcat -c");
		process = Runtime.getRuntime().exec(
			"logcat -f " + logFile + " *:S MainActivity:D");
	    } catch (IOException e) {
		e.printStackTrace();
	    }

	} else if (isExternalStorageReadable()) {
	    // only readable
	} else {
	    // not accessible
	}
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
	String state = Environment.getExternalStorageState();
	if (Environment.MEDIA_MOUNTED.equals(state)) {
	    return true;
	}
	return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
	String state = Environment.getExternalStorageState();
	if (Environment.MEDIA_MOUNTED.equals(state)
		|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	    return true;
	}
	return false;
    }
}
