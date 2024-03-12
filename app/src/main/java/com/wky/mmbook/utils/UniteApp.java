package com.wky.mmbook.utils;

import android.app.Application;

import com.wky.mmbook.db.DBManager;

public class UniteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDB(getApplicationContext());
    }
}
