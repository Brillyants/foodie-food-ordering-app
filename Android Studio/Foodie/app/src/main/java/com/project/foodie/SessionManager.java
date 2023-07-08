package com.project.foodie;

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

    // create set id method
    public void setId(int id) {
        editor.putInt("KEY_ID", id);
        editor.commit();
    }

    // create get id method
    public int getId() {
        return sharedPreferences.getInt("KEY_ID", -1);
    }

    // create set username method
    public void setUsername(String username) {
        editor.putString("KEY_USERNAME", username);
        editor.commit();
    }

    // create get username method
    public String getUsername() {
        return sharedPreferences.getString("KEY_USERNAME", "");
    }

    // create set name method
    public void setName(String name) {
        editor.putString("KEY_NAME", name);
        editor.commit();
    }

    // create get name method
    public String getName() {
        return sharedPreferences.getString("KEY_NAME", "");
    }
}
