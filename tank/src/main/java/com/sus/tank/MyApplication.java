package com.sus.tank;

import android.app.Application;

/**
 * Created by SuS on 2017/7/18.
 */

public class MyApplication extends Application {

    private static MyApplication app;

    public static MyApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

}

