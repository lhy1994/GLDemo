package com.lhy.learning.gldemo;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context sInstance;

    @Override
    public void onCreate() {
        sInstance = this;
        super.onCreate();
    }

    public static Context getInstance() {
        return sInstance;
    }
}
