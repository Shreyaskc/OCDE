package com.billion.ocde;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author Shreyas K C
 * Preference Helper for Adding Unique ID, Device ID and any other fields if necesary.
 */
public class PreferenceHelper {

    private SharedPreferences app_prefs;
    private final String PREF_NAME = "KINDNESS_APP_PREF";
    private final String UNIQUE_ID = "unique_id";
    private final String DEVICE_ID = "device_id";
    private final String NAME = "name";
    private final String EMAIL = "email";
    private final String REMEBER_ME = "remember";
    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
    }


    public void putUniqueId(String uniqueId) {
        Editor edit = app_prefs.edit();
        edit.putString(UNIQUE_ID, uniqueId);
        edit.commit();
    }

    public String getUniqueId() {
        return app_prefs.getString(UNIQUE_ID, null);
    }

    public void putDeviceId(String deviceId) {
        Editor edit = app_prefs.edit();
        edit.putString(DEVICE_ID, deviceId);
        edit.commit();
    }

    public String getDeviceId() {

        return app_prefs.getString(DEVICE_ID, null);
    }

    public void putEmail(String email) {
        Editor edit = app_prefs.edit();
        edit.putString(EMAIL, email);
        edit.commit();
    }

    public String getEmail() {
        return app_prefs.getString(EMAIL, "");
    }
    public void putName(String name) {
        Editor edit = app_prefs.edit();
        edit.putString(NAME, name);
        edit.commit();
    }

    public String getName() {
        return app_prefs.getString(NAME, "");
    }

    public void putRemeber(boolean remember) {
        Editor edit = app_prefs.edit();
        edit.putBoolean(REMEBER_ME, remember);
        edit.commit();
    }

    public boolean getRemeber() {
        return app_prefs.getBoolean(REMEBER_ME, false);
    }

}