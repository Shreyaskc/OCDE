/**
 * 
 */
package com.billion.ocde;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.billion.ocde.constants.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author Shreyas K C This async Task is for act call
 */
public class ActAsyncTask extends AsyncTask<String, Void, String> {

    public static String TAG = "";
    String url = "";
    Activity context;
    HashMap<String, Object> postData;
    ProgressDialog progressDialog;
	PreferenceHelper preferenceHelper;

    public ActAsyncTask(Activity context, HashMap<String, Object> postData,PreferenceHelper preferenceHelper) {
	this.context = context;
	this.postData = postData;
	this.preferenceHelper = preferenceHelper;
    }

    @Override
    protected void onPreExecute() {
	super.onPreExecute();
	progressDialog = new ProgressDialog(context);
	progressDialog.setMessage("Please wait...");
	progressDialog.show();
    }

    @Override
    protected String doInBackground(String... url) {
	try {
	    HttpPost httppost = new HttpPost(Constants.URL);
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		httppost.setHeader("Content-Type","application/json");

//	    for (String key : this.postData.keySet()) {
//		Object obj = this.postData.get(key);
//		String val = null;
//		if (obj != null) {
//		    val = obj.toString();
//		}
//		nvps.add(new BasicNameValuePair(key, val));
//	    }
		Gson gson = new Gson();
		String json = gson.toJson(postData);
		httppost.setEntity(new StringEntity(json));
		//httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

	    HttpClient httpclient = new DefaultHttpClient();

	    HttpResponse response = httpclient.execute(httppost);

	    HttpEntity entity = response.getEntity();
	    if (entity != null) {
		InputStream instream = entity.getContent();
		String result = Utility.convertStreamToString(instream);
		return result;
	    }
	    return null;
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	} catch (ClientProtocolException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    @Override
    protected void onPostExecute(String result) {
	super.onPostExecute(result);
	progressDialog.dismiss();
	try {
	    if (!TextUtils.isEmpty(result)
		    ) {
			Gson gson = new Gson();
			JsonElement element = gson.fromJson (result, JsonElement.class);
			if (element == null){
				Toast.makeText(context, Constants.ERROR, Toast.LENGTH_SHORT)
						.show();
				return;
			}
			JsonObject jsonObj = element.getAsJsonObject();
			if (jsonObj == null){
				Toast.makeText(context, Constants.ERROR, Toast.LENGTH_SHORT)
						.show();
				return;
			}
			element = jsonObj.get(Constants.UNIQUE_ID);
			if (element == null){
				Toast.makeText(context, Constants.ERROR, Toast.LENGTH_SHORT)
						.show();
				return;
			}
			String id = element.getAsString();
			//Check if the returned unique id is empty.
			if (TextUtils.isEmpty(id)){
				Toast.makeText(context, Constants.ERROR, Toast.LENGTH_SHORT)
						.show();
				return;
			}
			// Place the id in shared preference to be utilized in Share story.
			preferenceHelper.putUniqueId(id);
		try {
		    ((MainActivity) context).dialog.dismiss();
		} catch (Exception e) {
		    // TODO: handle exception
		}
		((MainActivity) context).showThanksMain();
		Toast.makeText(context, Constants.SUCCESSFULL,
			Toast.LENGTH_SHORT).show();
	    } else {
		Toast.makeText(context, Constants.ERROR, Toast.LENGTH_SHORT)
			.show();
	    }
	} catch (Exception e) {
	    e.printStackTrace();

	}
    }
}
