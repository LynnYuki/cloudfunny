package com.example.lynnyuki.cloudfunny.dagger.component;

import android.content.Context;

import com.example.lynnyuki.cloudfunny.dagger.module.ApplicationModule;
import com.example.lynnyuki.cloudfunny.dagger.module.HttpModule;
import com.example.lynnyuki.cloudfunny.model.db.LikeBeanGreenDaoManager;
import com.example.lynnyuki.cloudfunny.model.prefs.SharePrefManager;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * APP 全局组件
 */
@Singleton
@Component(modules = {ApplicationModule.class, HttpModule.class})
public interface AppComponent {

    Context getContext(); // 提供Context给子Component使用

    SharePrefManager getSharePrefManager();

    Retrofit.Builder getRetrofitBuilder();

    OkHttpClient getOkHttpClient();

    LikeBeanGreenDaoManager getGreenDaoManager();

}