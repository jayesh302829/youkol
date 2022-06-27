package com.dk.youkol;

import android.content.Intent;
import android.content.IntentFilter;

import androidx.appcompat.app.AppCompatDelegate;

public class Application extends android.app.Application {

    private static Application application;

    public static Application getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
