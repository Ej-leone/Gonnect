package com.op.bt.beneficiarypayments.Data;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    // Shared preferences file name
    private static final String PREF_NAME = "Bcredit";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // shared pref mode
    int PRIVATE_MODE = 0;

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * TODO:Add the is  boolean IS_LOgin =true
     * Create a Login Session
     */
    public void setAccesstoken(String nm) {
        editor.putString("at", nm);
        editor.commit();
    }

    public String getAccessToken() {
        return pref.getString("at", "User");
    }

    /**
     * TODO:Add the is  boolean IS_LOgin =true
     * Create a Login Session
     */
    public void setUser(String nm, String Photourl, String id) {
        editor.putString("Name", nm);
        editor.putString("uid", id);
        editor.putString("Photourl", Photourl);
        editor.commit();
    }


    public String GetUSer() {
        return pref.getString("Name", "User");
    }

    public String GetPhotourl() {
        return pref.getString("Photourl", "User");
    }

    public String Getid() {
        return pref.getString("uid", "User");
    }
}
