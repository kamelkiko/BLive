package com.expert.blive.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedPref {

    private final Context context;
    SharedPreferences.Editor prefsEditor;
    SharedPreferences sharedpreferences;

    public SharedPref(Context context) {
        this.context = context;
        sharedpreferences = context.getSharedPreferences("NicoLive", Context.MODE_PRIVATE);
        prefsEditor = sharedpreferences.edit();
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedpreferences.getString(key, "");
    }

    public void clearPreferences() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }

    public <T> T getModel(String key, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(sharedpreferences.getString(key, ""), type);
    }



    public void saveModel(String key, Object modelClass) {
        Gson gson = new Gson();
        prefsEditor.putString(key, gson.toJson(modelClass));
        prefsEditor.apply();
    }

    public void login(Context context, boolean connected) {
        sharedpreferences = context.getSharedPreferences("NicoLive", 0);
        prefsEditor = sharedpreferences.edit();
        prefsEditor.putBoolean("connected", connected);
        prefsEditor.commit();
    }

    public boolean isLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("NicoLive", 0);
        return sharedPreferences.getBoolean("connected", false);
    }
}
