package com.example.game_class;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class MySharedPreferences {

    private SharedPreferences prefs;


    public MySharedPreferences(Context context) {
        prefs = context.getSharedPreferences("MyPref", MODE_PRIVATE);

    }
    public String getString(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void removeKey(String key) {
        prefs.edit().remove(key);
    }
}