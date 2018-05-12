package com.example.lynnyuki.cloudfunny.config;

import android.app.Application;

//import com.example.lynnyuki.cloudfunny.dagger.component.DaggerAppComponent;
//import com.example.lynnyuki.cloudfunny.util.AppApplicationUtil;


public class CloudFunnyApplication extends Application {

    private static CloudFunnyApplication instance;
//    public static AppComponent appComponent;
    public static synchronized CloudFunnyApplication getInstance() { return instance; }



}
