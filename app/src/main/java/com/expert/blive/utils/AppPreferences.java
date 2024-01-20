package com.expert.blive.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class AppPreferences {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public AppPreferences(Context context, String name) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveStringValue(String key, String value){
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key){
        return sharedPreferences.getString(key,"");
    }

    public void clearPreferences(){
        editor.clear();
        editor.apply();
    }

    public <T> T getModel(String key, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(sharedPreferences.getString(key, ""), type);
    }

    public void saveModel(String key, Object modelClass){
        Gson gson = new Gson();
        editor.putString(key, gson.toJson(modelClass));
        editor.apply();
        Log.d("docWorld","Model saved");
    }


}
