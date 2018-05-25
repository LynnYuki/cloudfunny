package com.example.lynnyuki.cloudfunny.config;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.example.lynnyuki.cloudfunny.dagger.component.AppComponent;
import com.example.lynnyuki.cloudfunny.dagger.component.DaggerAppComponent;
import com.example.lynnyuki.cloudfunny.dagger.module.ApplicationModule;
import com.example.lynnyuki.cloudfunny.dagger.module.HttpModule;
import com.example.lynnyuki.cloudfunny.model.prefs.SharePrefManager;
import com.tencent.smtt.sdk.QbSdk;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * app应用类
 */

public class CloudFunnyApplication extends Application {

    private static CloudFunnyApplication instance;

    public static AppComponent appComponent;

    public static synchronized CloudFunnyApplication getInstance() { return instance; }
    @Override
    public void onCreate(){
        super.onCreate();

        instance = this;

        appComponent = DaggerAppComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();

        SharePrefManager sharePrefManager = appComponent.getSharePrefManager();

        boolean nightMode = sharePrefManager.getNightMode();
        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        sharePrefManager.setLocalMode(AppCompatDelegate.getDefaultNightMode());

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
              Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
    public static AppComponent getAppComponent() {
        return appComponent;
    }

}
