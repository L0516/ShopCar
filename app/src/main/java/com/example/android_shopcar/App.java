package com.example.android_shopcar;

import android.app.Application;

import org.xutils.x;

/**
 * Created by 刘文艺 on 2017/8/13.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
