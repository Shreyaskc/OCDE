package com.billion.ocde;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Adapter;
import android.widget.FrameLayout;

/**
 * 
 * @author Shreyas K C Utility class used in all
 * 
 */
public class Utility {

    /**
     * @Purpose Show alert according to the message
     */
    public static void showAlert(String msg, Context context) {
	try {
	    AlertDialog.Builder alert1 = new AlertDialog.Builder(context);
	    alert1.setTitle("Message"); // Set Alert dialog title here
	    alert1.setMessage(msg);
	    alert1.setCancelable(false);
	    alert1.setPositiveButton("OK",
		    new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
				int whichButton) {
			    dialog.cancel();
			}
		    });
	    AlertDialog alertDialog1 = alert1.create();
	    alertDialog1.setCancelable(false);
	    alertDialog1.show();
	} catch (Exception e) {
	    // Log.e("Exception", "showAlert..." + e.getMessage());
	}
    }

    /**
     * @Purpose convert stream to string
     */
    public static String convertStreamToString(InputStream is) {
	BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	StringBuilder sb = new StringBuilder();

	String line = null;
	try {
	    while ((line = reader.readLine()) != null) {
		sb.append(line + "\n");
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		is.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return sb.toString();
    }

    /**
     * Return Larger item size from adapter
     * 
     * @param context
     * @param adapter
     * @return int to get widest view
     */
    public static int getWidestView(Context context, Adapter adapter) {
	int maxWidth = 0;
	View view = null;
	FrameLayout fakeParent = new FrameLayout(context);
	for (int i = 0, count = adapter.getCount(); i < count; i++) {
	    view = adapter.getView(i, view, fakeParent);
	    view.measure(View.MeasureSpec.UNSPECIFIED,
		    View.MeasureSpec.UNSPECIFIED);
	    int width = view.getMeasuredWidth();
	    if (width > maxWidth) {
		maxWidth = width;
	    }
	}
	return maxWidth;
    }

    /**
     * Checking for internet connection
     * 
     */
    public static boolean isNetworkAvailable(Context context) {
	ConnectivityManager connectivity = (ConnectivityManager) context
		.getSystemService(Context.CONNECTIVITY_SERVICE);
	if (connectivity != null) {
	    NetworkInfo[] info = connectivity.getAllNetworkInfo();
	    if (info != null) {
		for (int i = 0; i < info.length; i++) {
		    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
			return true;
		    }
		}
	    }
	}
	return false;
    }

}
