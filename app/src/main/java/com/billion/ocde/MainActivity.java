package com.billion.ocde;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.billion.ocde.constants.Constants;

import me.angrybyte.circularslider.CircularSlider;

public class MainActivity extends Activity implements OnClickListener
	,CircularSlider.OnSliderMovedListener {
    public static long start_exit = (long) 0;
    LinearLayout mainLayout, singleLayout, multipleLayout;
    Button singleButton, multipleButton, singleAct, backButton, sButton, group,
	    groupReverse, shareButton, facebook, twitter, instagram;
    RelativeLayout relativeLayout, thanksLayout;
    ImageView confettiImage;
    boolean isSingle = true;
    boolean isGroupOpen = false;
	PreferenceHelper preferenceHelper;
    static String macAddress;
    VideoView confetti;
    // CircularSeekBar seekCount;
    TextView multipleCount, link;
    static int count = 0;
    Dialog dialog = null;
    EditText shareText, name, email, location;
    PopupWindow info;
    LayoutInflater infoLayoutInflater;
	MediaPlayer mediaPlayer;
	MediaPlayer single;
	MediaPlayer multiple;
	EditText rememberMeText;
	static MediaPlayer multipledial;
	static boolean isPlaying = false;
	static  long lastPlayTime = 0;
	// ImageView anm;
	CheckBox rememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	try {
	    setContentView(R.layout.activity_main);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	preferenceHelper = new PreferenceHelper(this);
	mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
	thanksLayout = (RelativeLayout) findViewById(R.id.thanksLayout);
	singleLayout = (LinearLayout) findViewById(R.id.singleLayout);
	multipleLayout = (LinearLayout) findViewById(R.id.multipleLayout);
	relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
	singleButton = (Button) findViewById(R.id.singleButton);
	multipleButton = (Button) findViewById(R.id.multipleButton);
	singleAct = (Button) findViewById(R.id.singleAct);
	backButton = (Button) findViewById(R.id.backButton);
	shareButton = (Button) findViewById(R.id.shareButton);
	confettiImage = (ImageView) findViewById(R.id.confettiImage);
		mediaPlayer = MediaPlayer.create(this, R.raw.tk);
		mediaPlayer.setLooping(false);
		multipledial = MediaPlayer.create(this, R.raw.multipledial);
		multipledial.setLooping(false);
	try {
	    sButton = (Button) findViewById(R.id.sButton);
	} catch (Exception e) {

	    Log.e("", e.getMessage());
	    printLog(getExceptionAsString(e));
	    e.printStackTrace();
	}

	multipleCount = (TextView) findViewById(R.id.multipleCount);
	group = (Button) findViewById(R.id.group);
//	CircularSeekBar seekbar = (CircularSeekBar) findViewById(R.id.seekCount);
//	seekbar.setOnSeekBarChangeListener(this);
		CircularSlider seekbar = (CircularSlider) findViewById(R.id.circular);
		seekbar.setOnSliderMovedListener(this);
		seekbar.setThumbImage(getResources().getDrawable( R.drawable.knob ));
		seekbar.setBorderColor(getResources().getColor(R.color.app_theme));
		seekbar.setBorderThickness(50);
		seekbar.setThumbSize(120);
	singleButton.setOnClickListener(this);
	multipleButton.setOnClickListener(this);
	singleAct.setOnClickListener(this);
	backButton.setOnClickListener(this);
	sButton.setOnClickListener(this);
	group.setOnClickListener(this);
	shareButton.setOnClickListener(this);
	// ImageView mImageViewFilling = (ImageView)
	// findViewById(R.id.imageview_animation_list_filling);
	// ((AnimationDrawable) mImageViewFilling.getBackground()).start();
	showSingleMain();
	isGroupOpen = false;
    }

    void setMultipleCount() {
		if (count == 1000){
			multipleCount.setText( "1,000");
		}
		else{
			multipleCount.setText(count + "");
		}

    }

    void showSingleMain() {
		stopThankYou();
	mainLayout.setVisibility(View.VISIBLE);
	singleLayout.setVisibility(View.VISIBLE);
	multipleLayout.setVisibility(View.GONE);
	thanksLayout.setVisibility(View.GONE);
	isSingle = true;
	singleButton.setBackgroundResource(R.drawable.select);
	multipleButton.setBackgroundResource(R.drawable.de_select);
    }

    void showMultipleMain() {
		stopThankYou();
	mainLayout.setVisibility(View.VISIBLE);
	multipleLayout.setVisibility(View.VISIBLE);
	singleLayout.setVisibility(View.GONE);
	thanksLayout.setVisibility(View.GONE);
	isSingle = false;
	setMultipleCount();
	multipleButton.setBackgroundResource(R.drawable.select);
	singleButton.setBackgroundResource(R.drawable.de_select);
    }

    void showThanksMain() {

	mainLayout.setVisibility(View.GONE);
	thanksLayout.setVisibility(View.VISIBLE);
	confettiImage.bringToFront();
		playThankYou();
	try {
	    System.gc();
	    confettiImage.setBackgroundResource(R.drawable.confetti);
	    ((AnimationDrawable) confettiImage.getBackground()).start();
	} catch (Exception e) {
	    printLog(getExceptionAsString(e));
	    Log.e("", e.getMessage());
	} catch (OutOfMemoryError e) {
	    System.gc();
	    printLog(getOutOfMemoryErrorAsString(e));
	    Log.e("", e.getMessage());
	    try {
		System.gc();
		confettiImage.setBackgroundResource(R.drawable.confetti_less1);
		((AnimationDrawable) confettiImage.getBackground()).start();
	    } catch (Exception e2) {
		printLog(getExceptionAsString(e2));
		Log.e("", e.getMessage());
	    } catch (OutOfMemoryError e3) {
		System.gc();
		printLog(getOutOfMemoryErrorAsString(e));
		Log.e("", e.getMessage());
		try {
		    System.gc();
		    confettiImage
			    .setBackgroundResource(R.drawable.confetti_less2);
		    ((AnimationDrawable) confettiImage.getBackground()).start();
		} catch (Exception e1) {
		    printLog(getExceptionAsString(e1));
		    Log.e("", e.getMessage());
		} catch (OutOfMemoryError e2) {
		    System.gc();
		    printLog(getOutOfMemoryErrorAsString(e));
		    Log.e("", e.getMessage());
		}

	    }

	}

	// ((AnimationDrawable) thanksLayout.getBackground()).start();
    }
	private void playThankYou(){
		try {
			mediaPlayer = MediaPlayer.create(this, R.raw.tk);
			mediaPlayer.setLooping(false);
			mediaPlayer.start();
		}
		catch (Exception e){
			Log.e("", e.getMessage());
			printLog(getExceptionAsString(e));
			e.printStackTrace();
		}
	}
	private void stopThankYou(){
		try {
			mediaPlayer.stop();
		}
		catch (Exception e){
			Log.e("", e.getMessage());
			printLog(getExceptionAsString(e));
			e.printStackTrace();
		}
	}
	private void playSingle(){
		try {
			single = MediaPlayer.create(this, R.raw.single);
			single.setLooping(false);
			single.start();
		}
		catch (Exception e){
			Log.e("", e.getMessage());
			printLog(getExceptionAsString(e));
			e.printStackTrace();
		}
	}
	private void stopSingle(){
		try {
			single.stop();
		}
		catch (Exception e){
			Log.e("", e.getMessage());
			printLog(getExceptionAsString(e));
			e.printStackTrace();
		}
	}
	private void playMultiple(){
		try {
			multiple = MediaPlayer.create(this, R.raw.multiple);
			multiple.setLooping(false);
			multiple.start();
		}
		catch (Exception e){
			Log.e("", e.getMessage());
			printLog(getExceptionAsString(e));
			e.printStackTrace();
		}
	}
	private void stopmMultiple(){
		try {
			multiple.stop();
		}
		catch (Exception e){
			Log.e("", e.getMessage());
			printLog(getExceptionAsString(e));
			e.printStackTrace();
		}
	}

	private void playMultipleDial(){
		try {
			multipledial = MediaPlayer.create(this, R.raw.multipledial);
			multipledial.setLooping(false);
			multipledial.start();
		}
		catch (Exception e){
			Log.e("", e.getMessage());
			printLog(getExceptionAsString(e));
			e.printStackTrace();
		}
	}

	private void stopmMultipleDial(){
		try {
			multipledial.stop();
		}
		catch (Exception e){
			Log.e("", e.getMessage());
			printLog(getExceptionAsString(e));
			e.printStackTrace();
		}
	}
    @Override
    public void onBackPressed() {
	if (isGroupOpen) {
	    closeLearnInfo();
	    return;
	}
	long currTime = System.currentTimeMillis();
	if ((currTime - start_exit) < 4000) {
	    moveTaskToBack(true);
	    android.os.Process.killProcess(android.os.Process.myPid());
	    System.exit(1);
	}
	start_exit = System.currentTimeMillis();
	Toast.makeText(this, Constants.TOAST_MAIN_EXIT, Toast.LENGTH_SHORT)
		.show();
    }

    Request getRequest(int count) {
	Request request = new Request();
	request.setCount(count + "");
	request.setDeviceID(getMacAddress());
	request.setCreateBy(getMacAddress());
	return request;
    }

    Request getRequest(int count, String name, String emailAddress, String story) {
	Request request = new Request();
	request.setCount(count + "");
	request.setDeviceID(getMacAddress());
	request.setCreateBy(getMacAddress());
	request.setEmailAddress(emailAddress);
	request.setName(name);
	request.setStory(story);

	return request;
    }
	Request getRequest(int count, String name, String emailAddress, String story,String location) {
		Request request = new Request();
		request.setCount(count + "");
		request.setDeviceID(getMacAddress());
		request.setCreateBy(getMacAddress());
		request.setEmailAddress(emailAddress);
		request.setName(name);
		request.setStory(story);
		request.setLocation(location);
		return request;
	}
    void callKindnessApi(Request request) {
	HashMap<String, Object> postBody = getPostBody(request);
	new ActAsyncTask(MainActivity.this, postBody,preferenceHelper).execute();
    }

    void closeLearnInfo() {
	setLayoutTransparent(1f);
	group.setBackgroundResource(R.drawable.group);
	try {
	    info.dismiss();

	} catch (Exception e) {
	    printLog(getExceptionAsString(e));
	}
	isGroupOpen = false;
    }

    @Override
    public void onClick(View v) {
	try {
	    Thread.sleep(175);
	} catch (InterruptedException e) {
	    printLog(getExceptionAsString(e));
	}
	String uniqueId = preferenceHelper.getUniqueId();
	switch (v.getId()) {
	case R.id.singleButton:
	    showSingleMain();
	    break;
	case R.id.multipleButton:
	    showMultipleMain();
	    break;
	case R.id.singleAct:
	    Request request = getRequest(1);
	    callKindnessApi(request);
		playSingle();
	    break;
	case R.id.sButton:
	    if (count == 0) {
		Toast.makeText(this, Constants.COUNT_ZERO, Toast.LENGTH_SHORT)
			.show();
		break;
	    }
	    Request multipleRequest = getRequest(count);
	    callKindnessApi(multipleRequest);
		playMultiple();
	    break;
	case R.id.group:
	    if (isGroupOpen) {
		closeLearnInfo();
	    } else {
		showLearnMoreDialog();
		isGroupOpen = true;
	    }

	    break;
	case R.id.shareButton:
		uniqueId = preferenceHelper.getUniqueId();
		if (TextUtils.isEmpty(uniqueId) || "-1".equalsIgnoreCase(uniqueId)){
			Toast.makeText(this, Constants.REQUEST_TIMED_OUT, Toast.LENGTH_SHORT)
					.show();
			showSingleMain();
			return;
		}
	    try {
		showShareDialog();
		email.setText(preferenceHelper.getEmail());
		name.setText(preferenceHelper.getName());
	    } catch (Exception e) {
		printLog(getExceptionAsString(e));
	    }

	    break;
	case R.id.backButton:
		stopThankYou();
		// Place the -1 to signify the story is submitted.
		preferenceHelper.putUniqueId("-1");
	    if (isSingle) {
		showSingleMain();
	    } else {
		showMultipleMain();
	    }
	    break;

	case R.id.cancel:
	    if (dialog != null) {
		try {
		    dialog.dismiss();
		} catch (Exception e) {
		    printLog(getExceptionAsString(e));
		}

	    }
	    break;

	case R.id.send:
		uniqueId = preferenceHelper.getUniqueId();
		if (TextUtils.isEmpty(uniqueId) || "-1".equalsIgnoreCase(uniqueId)){
			Toast.makeText(this, Constants.REQUEST_TIMED_OUT, Toast.LENGTH_SHORT)
					.show();
			showSingleMain();
			return;
		}

			try {
		if (shareText != null && email != null && name != null
			&& location != null) {
		    String shareVal = shareText.getText().toString();
		    String emailVal = email.getText().toString();
		    String nameVal = name.getText().toString();
			String locationVal = location.getText().toString();
		    if (TextUtils.isEmpty(shareVal)) {
			Toast.makeText(this, Constants.EMPTY_VALUES,
				Toast.LENGTH_SHORT).show();
			break;
		    }
			if (!TextUtils.isEmpty(emailVal)){
				if (!eMailValidation(emailVal)){
					Toast.makeText(this, Constants.VALID_EMAIL,
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		    // String locationVal = location.getText().toString();
		    int countVal = 1;
		    if (!isSingle) {
			countVal = this.count;
		    }
		    Request req = getRequest(countVal, nameVal, emailVal,
			    shareVal,locationVal);
			//If remember Me is checked.
			try {
				if (rememberMe.isChecked()){
					preferenceHelper.putRemeber(true);
				if (!TextUtils.isEmpty(nameVal)
						&& !TextUtils.isEmpty(emailVal)
						) {
					preferenceHelper.putEmail(emailVal);
					preferenceHelper.putName(nameVal);
				}
				else{
					Toast.makeText(this, Constants.NOT_STORING,
							Toast.LENGTH_SHORT).show();
				}}
				else {
					preferenceHelper.putRemeber(false);
				}
			}catch (Exception e){
				Toast.makeText(this, Constants.COULD_NOT_STORE,
						Toast.LENGTH_SHORT).show();
			}
			HashMap<String, Object> postBody = getPostBody(req);
			new ActAsyncTaskWithStory(MainActivity.this, postBody,preferenceHelper).execute();

		    //callKindnessApi(req);
		}
	    } catch (Exception e) {
		printLog(getExceptionAsString(e));
		Toast.makeText(this, Constants.INVALID_VALUES,
			Toast.LENGTH_SHORT).show();
	    }
	    // TODO: handle exception

	    break;

	case R.id.facebook:
	    try {
		Intent intent = new Intent(
			Intent.ACTION_VIEW,
			Uri.parse("https://www.facebook.com/sharer/sharer.php?u=www.kindness1billion.org"));
		startActivity(intent);
	    } catch (Exception e) {
		printLog(getExceptionAsString(e));
		try {
		    startActivity(new Intent(Intent.ACTION_VIEW,
			    Uri.parse("http://www.facebook.com/")));
		} catch (Exception e1) {
		    printLog(getExceptionAsString(e1));
		}
	    }
	    break;
	case R.id.twitter:
	    try {
		startActivity(new Intent(
			Intent.ACTION_VIEW,
			Uri.parse("twitter://post?message=One%20Billion%20Acts%20of%20Kindness%20is%20an%20initiative%20launched%20by%20the%20Orange%20County%20Department%20of%20Education.%20http%3A//www.kindness1billion.org")));
	    } catch (Exception e) {
		printLog(getExceptionAsString(e));
		try {
		    startActivity(new Intent(
			    Intent.ACTION_VIEW,
			    Uri.parse("https://mobile.twitter.com/compose/tweet?text=One%20Billion%20Acts%20of%20Kindness%20is%20an%20initiative%20launched%20by%20the%20Orange%20County%20Department%20of%20Education.%20http%3A%2F%2Fwww.kindness1billion.org&source=webclient")));
		} catch (Exception e1) {
		    printLog(getExceptionAsString(e1));
		}
	    }
	    break;
	case R.id.instagram:
	    try {
		Uri uri = Uri.parse("instagram://app?id=1");
		Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

		likeIng.setPackage("com.instagram.android");
		startActivity(likeIng);
	    } catch (Exception e) {
		printLog(getExceptionAsString(e));
		try {
		    startActivity(new Intent(Intent.ACTION_VIEW,
			    Uri.parse("https://instagram.com")));
		} catch (Exception e1) {
		    printLog(getExceptionAsString(e1));
		}
	    }
	    break;
	case R.id.link:
	    try {
		startActivity(new Intent(Intent.ACTION_VIEW,
			Uri.parse("http://kindness1billion.org/")));
	    } catch (Exception e) {
		printLog(getExceptionAsString(e));
		e.printStackTrace();
	    }
	    break;
	default:
	}

    }

    private HashMap<String, Object> getPostBody(Request request) {
	HashMap<String, Object> postData = new LinkedHashMap<String, Object>();
	postData.put(Constants.CREATED_BY, request.getCreateBy());
	postData.put(Constants.EMAIL, request.getEmailAddress());
	postData.put(Constants.DEVICE_ID, request.getDeviceID());
	postData.put(Constants.COUNT, request.getCount());
	postData.put(Constants.NAME, request.getName());
	postData.put(Constants.STORY, request.getStory());
	postData.put(Constants.UNIQUE_ID, preferenceHelper.getUniqueId());
		postData.put(Constants.LOCATION, request.getLocation());
	return postData;
    }

    private String getMacAddress() {
	macAddress = preferenceHelper.getDeviceId();
	if (!TextUtils.isEmpty(macAddress)) {

	    return macAddress;
	}
	TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
	macAddress = mngr.getDeviceId();
	preferenceHelper.putDeviceId(macAddress);
	return macAddress;
    }


	@Override
	public void onSliderMoved(double pos){
		long currentTime =  System.currentTimeMillis();
		if((currentTime - lastPlayTime)>750) {
			playMultipleDial();
		}
		lastPlayTime = currentTime;

		count = (int)((pos * 1000));
		if (count<0){
			count = count*-1;
		}
		else if (count>0){
			count= (1000 - count)+1;
		}
		setMultipleCount();

	}


    private void showShareDialog() {
	dialog = new Dialog(this);
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	dialog.setContentView(R.layout.share_dialog);
	dialog.setCancelable(false);
	dialog.setCanceledOnTouchOutside(false);
	Button cancel = (Button) dialog.findViewById(R.id.cancel);
	Button send = (Button) dialog.findViewById(R.id.send);
	LinearLayout shareLayout = (LinearLayout) dialog
		.findViewById(R.id.shareLayout);
	shareLayout.setAlpha(0.8f);
	shareText = (EditText) dialog.findViewById(R.id.shareText);
		rememberMe = (CheckBox) dialog.findViewById(R.id.rememberMe);
		if (preferenceHelper.getRemeber()){
			rememberMe.setChecked(true);
		}
		else{
			rememberMe.setChecked(false);
		}
		rememberMeText = (EditText) dialog.findViewById(R.id.rememberMeText);
rememberMeText.setEnabled(false);
	name = (EditText) dialog.findViewById(R.id.name);
	email = (EditText) dialog.findViewById(R.id.email);
	location = (EditText) dialog.findViewById(R.id.location);
	cancel.setOnClickListener(this);
	send.setOnClickListener(this);

	dialog.show();
    }

    private void showLearnMoreDialog() {
	setLayoutTransparent(0.5f);
	group.setBackgroundResource(R.drawable.group_reverse);
	infoLayoutInflater = (LayoutInflater) getApplicationContext()
		.getSystemService(LAYOUT_INFLATER_SERVICE);

	View layout = infoLayoutInflater.inflate(R.layout.learn_more,
		(ViewGroup) findViewById(R.id.learnMoreLayout));
	// create a 300px width and 470px height PopupWindow
	// pw = new PopupWindow(layout, 300, 470, true);
	// ViewGroup container = (ViewGroup) infoLayoutInflater.inflate(
	// R.layout.learn_more, null);
	info = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT,
		ViewGroup.LayoutParams.WRAP_CONTENT, false);
	LinearLayout learnMoreLayout = (LinearLayout) layout
		.findViewById(R.id.learnMoreLayout);

	facebook = (Button) layout.findViewById(R.id.facebook);
	twitter = (Button) layout.findViewById(R.id.twitter);
	instagram = (Button) layout.findViewById(R.id.instagram);
	link = (TextView) layout.findViewById(R.id.link);
	facebook.setOnClickListener(this);
	twitter.setOnClickListener(this);
	instagram.setOnClickListener(this);
	link.setOnClickListener(this);
	learnMoreLayout.setAlpha(0.8f);
	info.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT,
		ViewGroup.LayoutParams.WRAP_CONTENT);
	info.setHeight(1);
	info.setWidth(1);
	info.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
    }

    void setLayoutTransparent(float val) {
	mainLayout.setAlpha(val);
	thanksLayout.setAlpha(val);
    }

    public void sendmail() {

	File myFile = new File(Environment.getExternalStorageDirectory()
		+ "/OCDE_Error.doc");
	Uri uri = Uri.fromFile(myFile);

	String emailAddress[] = { "shreyas@troomobile.com" };
	Intent emailIntent = new Intent(Intent.ACTION_SEND);
	emailIntent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
	emailIntent.putExtra(Intent.EXTRA_SUBJECT, "AackAack File");
	emailIntent.setType("text/plain");
	emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
	emailIntent.putExtra(Intent.EXTRA_STREAM, uri);

	final PackageManager pm = this.getPackageManager();
	final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent,
		0);

	ResolveInfo best = null;
	for (final ResolveInfo info : matches)
	    if (info.activityInfo.packageName.endsWith(".gm")
		    || info.activityInfo.name.toLowerCase().contains("gmail"))
		best = info;
	if (best != null)
	    emailIntent.setClassName(best.activityInfo.packageName,
		    best.activityInfo.name);
	startActivity(emailIntent);
    }

    public static void printLog(String message) {

	BufferedWriter bw = null;
	FileWriter fw = null;

	try {
	    fw = new FileWriter(Environment.getExternalStorageDirectory()
		    + "/OCDE/log/logcat.txt");
	    bw = new BufferedWriter(fw);
	    bw.write(message);

	} catch (IOException e) {

	    e.printStackTrace();

	} finally {

	    try {

		if (bw != null)
		    bw.close();

		if (fw != null)
		    fw.close();

	    } catch (IOException ex) {

		ex.printStackTrace();

	    }

	}
    }

    static String getOutOfMemoryErrorAsString(OutOfMemoryError e) {
	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	e.printStackTrace(pw);
	return sw.toString();
    }

    static String getExceptionAsString(Exception e) {
	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	e.printStackTrace(pw);
	return sw.toString();
    }
	public static boolean eMailValidation(String emailstring) {
		if (null == emailstring || emailstring.length() == 0) {
			return false;
		}
		Pattern emailPattern = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher emailMatcher = emailPattern.matcher(emailstring);
		return emailMatcher.matches();
	}
}
