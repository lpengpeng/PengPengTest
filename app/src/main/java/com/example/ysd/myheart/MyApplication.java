package com.example.ysd.myheart;

import android.app.Application;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

/**
 * Created by YSD on 2016/9/27.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);
        Logger.setDebug(true);
    }
}
