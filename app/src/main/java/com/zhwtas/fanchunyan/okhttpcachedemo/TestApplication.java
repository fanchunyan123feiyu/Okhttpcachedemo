package com.zhwtas.fanchunyan.okhttpcachedemo;

import android.app.Application;

import org.xutils.x;

/**
 * Created by FacChunYan on 2017/5/31.
 */

public class TestApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
