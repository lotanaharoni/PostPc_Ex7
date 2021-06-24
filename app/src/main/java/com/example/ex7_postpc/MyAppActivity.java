package com.example.ex7_postpc;

import android.app.Application;

public class MyAppActivity extends Application {
    private static MyAppActivity appInstance = null;
    private static MyLocalDb localDb;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        localDb = new MyLocalDb(this);
    }

    public static MyAppActivity getAppInstance() {
        return appInstance;
    }

    public static MyLocalDb getLocalDb() {
        return localDb;
    }
}
