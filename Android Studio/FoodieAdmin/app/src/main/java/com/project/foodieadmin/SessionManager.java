package com.project.foodieadmin;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    // initialize variable
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // create constructor
    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("AppKey", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    // create set login method
    public void setLogin(boolean login) {
        editor.putBoolean("KEY_LOGIN", login);
        editor.commit();
    }

    // create get login method
    public boolean getLogin() {
        return sharedPreferences.getBoolean("KEY_LOGIN", false);
    }
}
