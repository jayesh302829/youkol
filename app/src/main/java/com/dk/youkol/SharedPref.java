package com.dk.youkol;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SharedPref {
    private static SharedPreferences sharedPreferencesAd;
    private static SharedPreferences sharedPreferencesLanguage;
    private static SharedPreferences sharedPreferencesQuotes;

    private static SharedPreferences.Editor Ad_editor;
    private static SharedPreferences.Editor Language_editor;
    private static SharedPreferences.Editor Quotes_editor;

    public static final String MY_PREFERENCE = "Photo_Widgets_ISO_15";
    private static final String ADTime = "prefsAdTimes";
    private static final String Last_Time_AD_Show = "Last_Time_AD_Show";

    public static void init(Context context){
        sharedPreferencesAd = context.getSharedPreferences(ADTime, Context.MODE_PRIVATE);
        Ad_editor = sharedPreferencesAd.edit();
    }

    public static void setLastAdTime(Long adTime){
        Ad_editor = sharedPreferencesAd.edit();
        Ad_editor.putLong(Last_Time_AD_Show, adTime);
        Ad_editor.commit();
    }

    public static Long getLasttimewithAdtime(){
        Long Adtimewithsecond = 0L;
        try {
            Adtimewithsecond = sharedPreferencesAd.getLong(Last_Time_AD_Show, 0L);
        }catch (Exception e){
            Log.e("TAG", "getLasttimewithAdtime: "+e.getMessage() );
        }
        return Adtimewithsecond;
    }



}
